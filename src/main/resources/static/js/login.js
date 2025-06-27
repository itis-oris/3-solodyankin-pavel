function toggleResetForm() {
    const form = document.getElementById('password-reset-form');
    form.style.display = (form.style.display === 'none' || form.style.display === '') ? 'block' : 'none';
}
function logout() {
    fetch('/electron/auth/logout', {
        method: 'POST',
    }).then(response => {
        if (response.ok) {
            window.location.href = '/electron/home';
        }
    });
}
