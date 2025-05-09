document.getElementById('logoutForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    try {
        const response = await fetch('/logout', {
            method: 'POST',
            credentials: 'include'
        });

        if (response.ok) {
            localStorage.clear(); // Очистка данных
            sessionStorage.clear();
            window.location.href = '/login-page';
        }
    } catch (error) {
        console.error('Error logging out:', error);
    }
});

function loadCurrentUser() {
    fetch('/api/current-user')
        .then(response => response.json())
        .then(user => {
            const currentUserEmail = document.getElementById('currentUserEmail');
            currentUserEmail.textContent = user.email;
            currentUserEmail.dataset.userId = user.id;
            document.getElementById('currentUserRoles').textContent =
                user.roles.map(role => role.name.replace('ROLE_', '')).join(', ');

            renderUsersTable(user);
        })
        .catch(error => {
            console.error('Error loading current user:', error);
        })
}

function createCell(content) {
    const td = document.createElement('td');
    td.textContent = content;
    return td;
}

function renderUsersTable(user) {
    const tbody = document.getElementById('usersTableBodyById');
    tbody.innerHTML = '';

    const tr = document.createElement('tr');
    tr.appendChild(createCell(user.id));
    tr.appendChild(createCell(user.username));
    tr.appendChild(createCell(user.email));
    tr.appendChild(createCell(user.roles.map(role => role.name.replace('ROLE_', '')).join(', ')));
    tbody.appendChild(tr);
}

document.addEventListener('DOMContentLoaded', async () => {
    try {
        await loadCurrentUser();
    } catch (error) {
        console.error('Error initializing page:', error);
    }
});