function displayUsers(data, title = "Результат") {
    const resultsDiv = document.getElementById("results");
    resultsDiv.innerHTML = "";

    const h2 = document.createElement("h2");
    h2.textContent = title;
    resultsDiv.appendChild(h2);

    if (!data || data.length === 0) {
        const p = document.createElement("p");
        p.className = "empty-message";
        p.textContent = "Нет данных";
        resultsDiv.appendChild(p);
        return;
    }

    const ul = document.createElement("ul");
    data.forEach(user => {
        const li = document.createElement("li");
        li.textContent = `${user.name}, ${user.age} лет, номер телефона - ${user.phone}, звание - ${user.rank}, спорт - ${user.sport}`;
        ul.appendChild(li);
    });

    resultsDiv.appendChild(ul);
}

function getAllCoaches() {
    fetch('/electron/user/coaches')
        .then(response => response.json())
        .then(data => displayUsers(data, 'Тренеры'))
        .catch(err => alert('Ошибка загрузки тренеров'));
}

function getAllSportsmen() {
    fetch('/electron/user/sportsmen')
        .then(response => response.json())
        .then(data => displayUsers(data, 'Спортсмены'))
        .catch(err => alert('Ошибка загрузки спортсменов'));
}
