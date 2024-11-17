
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
        