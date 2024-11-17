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

    return author_stats, total_lines

def generate_html_report(repo_path, output_file='authorship_report.html'):
    files = subprocess.run(
        ['git', 'ls-files'], stdout=subprocess.PIPE,
        text=True, cwd=repo_path, encoding='utf-8'
    ).stdout.splitlines()

    contributors = {}  # {author: {file_path: lines}}
    total_lines_per_author = {}  # {author: total_lines_contributed}

    # Process files and data
    for file_path in files:
        try:
            authorship, total_lines = get_authorship(file_path, repo_path)

            # Update contributors dict
            for author, lines in authorship.items():
                if author not in contributors:
                    contributors[author] = {}
                contributors[author][file_path] = lines

                total_lines_per_author[author] = total_lines_per_author.get(author, 0) + lines

        except subprocess.CalledProcessError as e:
            print(f"Error processing {file_path}: {e}")

    # generate contributors select options
    contributors_options = '<option value="">All Contributors</option>\n'
    for contributor in sorted(contributors.keys()):
        total_lines = total_lines_per_author[contributor]
        contributors_options += f'<option value="{contributor}">{contributor} ({total_lines} lines)</option>\n'

    html_content = f"""
    <html>
    <head>
        <title>Authorship Report</title>
        <link rel="stylesheet" href="styles.css">
        <script src="scripts.js" defer></script>
    </head>
    <body>
        <h1>Authorship Report</h1>
        <input type="text" id="searchInput" onkeyup="applyFilters()" placeholder="Search by file or author name...">

        <select id="contributorSelect" onchange="applyFilters()">
            {contributors_options}
        </select>

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
            authorship, total_lines = get_authorship(file_path, repo_path)

            # Compute percentages
            if total_lines > 0:
                for author in authorship:
                    authorship[author] = round((authorship[author] / total_lines) * 100, 2)

            # Sort authorship by percentage in descending order
            sorted_authors = sorted(authorship.items(), key=lambda x: x[1], reverse=True)
            authors_info = "<br>".join([f"{author}: {percent}%" for author, percent in sorted_authors])

            # For filtering, add data-authors attribute to the row
            authors_in_file = ','.join([author for author in authorship.keys()])
            html_content += f"""
            <tr data-authors="{authors_in_file}">
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
            input, select { width: 100%; padding: 10px; margin-bottom: 12px; border: 1px solid #ddd; }
        """)

    # Create external JavaScript
    with open('scripts.js', 'w', encoding='utf-8') as js_file:
        js_file.write("""
            function applyFilters() {
                let input = document.getElementById('searchInput').value.toLowerCase();
                let contributorFilter = document.getElementById('contributorSelect').value.toLowerCase();
                let rows = document.querySelectorAll('tbody tr');
                rows.forEach(row => {
                    let file = row.cells[0].innerText.toLowerCase();
                    let author = row.cells[1].innerText.toLowerCase();
                    let matchesSearch = file.includes(input) || author.includes(input);
                    let matchesContributor = contributorFilter === '' || row.getAttribute('data-authors').toLowerCase().includes(contributorFilter);
                    row.style.display = matchesSearch && matchesContributor ? '' : 'none';
                });
            }
        """)

if __name__ == '__main__':
    repo_path = r'C:\Users\ileka\Desktop\se452-group-project'
    generate_html_report(repo_path)
