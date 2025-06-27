function toggleAddCoachForm() {
    const form = document.getElementById("addCoachForm");
    form.style.display = form.style.display === "none" || form.style.display === "" ? "block" : "none";
}

function validateCheckboxSelection(name) {
    const checkboxes = document.querySelectorAll(`input[name="${name}"]`);
    for (let checkbox of checkboxes) {
        if (checkbox.checked) {
            return true; // Если хотя бы один чекбокс выбран, форма отправляется
        }
    }
    alert("Пожалуйста, выберите хотя бы один вариант.");
    return false; // Если ни один чекбокс не выбран, форма не отправляется
}

