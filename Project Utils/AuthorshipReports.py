import os
import subprocess

def get_authorship(file_path, repo_path):
    cmd = ['git', 'blame', '--line-porcelain', file_path]
    result = subprocess.run(
        cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE,
        text=True, cwd=repo_path, encoding='utf-8', errors='replace'
    )

    author_stats = {}
    total_lines = 0

    for line in result.stdout.splitlines():
        if line.startswith('author '):
            author = line.split('author ')[1]
            author_stats[author] = author_stats.get(author, 0) + 1
            total_lines += 1

    if total_lines > 0:
        for author in author_stats:
            author_stats[author] = round((author_stats[author] / total_lines) * 100, 2)

    return author_stats

def generate_html_report(repo_path, output_file='authorship_report.html'):
    files = subprocess.run(
        ['git', 'ls-files'], stdout=subprocess.PIPE,
        text=True, cwd=repo_path, encoding='utf-8'
    ).stdout.splitlines()

    html_content = """
    <html>
    <head>
        <title>Authorship Report</title>
        <link rel="stylesheet" href="styles.css">
        <script src="scripts.js" defer></script>
    </head>
    <body>
        <h1>Authorship Report</h1>
        <input type="text" id="searchInput" onkeyup="searchTable()" placeholder="Search by file or author name...">

        <table>
            <thead>
                <tr>
                    <th>File</th>
                    <th>Authors & Contribution (%)</th>
                </tr>
            </thead>
            <tbody>
    """

    for file_path in files:
        try:
            authorship = get_authorship(file_path, repo_path)
            # Sort authorship by percentage in descending order
            sorted_authors = sorted(authorship.items(), key=lambda x: x[1], reverse=True)
            authors_info = "<br>".join([f"{author}: {percent}%" for author, percent in sorted_authors])
            html_content += f"""
            <tr>
                <td>{file_path}</td>
                <td>{authors_info}</td>
            </tr>
            """
        except subprocess.CalledProcessError as e:
            print(f"Error processing {file_path}: {e}")

    html_content += """
            </tbody>
        </table>
    </body>
    </html>
    """

    with open(output_file, 'w', encoding='utf-8') as f:
        f.write(html_content)

    print(f'Report generated: {output_file}')

    # Create external CSS
    with open('styles.css', 'w', encoding='utf-8') as css_file:
        css_file.write("""
            body { font-family: Arial, sans-serif; margin: 20px; }
            table { width: 100%; border-collapse: collapse; margin-top: 10px; }
            th, td { border: 1px solid #ddd; padding: 8px; }
            th { background-color: #4CAF50; color: white; }
            tr:nth-child(even) { background-color: #f2f2f2; }
            input { width: 100%; padding: 10px; margin-bottom: 12px; border: 1px solid #ddd; }
        """)

    # Create external JavaScript
    with open('scripts.js', 'w', encoding='utf-8') as js_file:
        js_file.write("""
            function searchTable() {
                let input = document.getElementById('searchInput').value.toLowerCase();
                let rows = document.querySelectorAll('tbody tr');
                rows.forEach(row => {
                    let file = row.cells[0].innerText.toLowerCase();
                    let author = row.cells[1].innerText.toLowerCase();
                    row.style.display = (file.includes(input) || author.includes(input)) ? '' : 'none';
                });
            }
        """)



if __name__ == '__main__':
    repo_path = r'C:\Users\ileka\Desktop\se452-group-project'
    generate_html_report(repo_path)
