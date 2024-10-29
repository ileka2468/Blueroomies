
            function searchTable() {
                let input = document.getElementById('searchInput').value.toLowerCase();
                let rows = document.querySelectorAll('tbody tr');
                rows.forEach(row => {
                    let file = row.cells[0].innerText.toLowerCase();
                    let author = row.cells[1].innerText.toLowerCase();
                    row.style.display = (file.includes(input) || author.includes(input)) ? '' : 'none';
                });
            }
        