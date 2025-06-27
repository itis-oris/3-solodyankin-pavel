// carousel.js
document.addEventListener('DOMContentLoaded', function() {
    const carousel = document.querySelector('.carousel-container');
    const slides = document.querySelectorAll('.carousel-slide');
    const totalSlides = slides.length;
    let currentIndex = 0;
    let autoplayInterval;
    const autoplayDelay = 5000; // 5 секунд между слайдами

    // Инициализация карусели
    function initCarousel() {
        // Показываем первый слайд
        showSlide(currentIndex);

        // Запускаем автопрокрутку
        startAutoplay();

        // Остановка автопрокрутки при наведении
        carousel.addEventListener('mouseenter', pauseAutoplay);
        carousel.addEventListener('mouseleave', startAutoplay);
    }

    // Показать конкретный слайд
    function showSlide(index) {
        // Скрываем все слайды
        slides.forEach(slide => {
            slide.style.opacity = '0';
            slide.style.display = 'none';
        });

        // Показываем текущий слайд с анимацией
        slides[index].style.display = 'block';
        setTimeout(() => {
            slides[index].style.opacity = '1';
            slides[index].style.transition = 'opacity 0.5s ease-in-out';
        }, 50);
    }

    // Следующий слайд
    function nextSlide() {
        currentIndex = (currentIndex + 1) % totalSlides;
        showSlide(currentIndex);
    }

    // Предыдущий слайд
    function prevSlide() {
        currentIndex = (currentIndex - 1 + totalSlides) % totalSlides;
        showSlide(currentIndex);
    }

    // Автопрокрутка
    function startAutoplay() {
        pauseAutoplay();
        autoplayInterval = setInterval(nextSlide, autoplayDelay);
    }

    // Пауза автопрокрутки
    function pauseAutoplay() {
        clearInterval(autoplayInterval);
    }

    // Добавляем кнопки навигации
    function addNavigationButtons() {
        const prevBtn = document.createElement('button');
        prevBtn.className = 'carousel-btn prev';
        prevBtn.innerHTML = '❮';
        prevBtn.addEventListener('click', prevSlide);

        const nextBtn = document.createElement('button');
        nextBtn.className = 'carousel-btn next';
        nextBtn.innerHTML = '❯';
        nextBtn.addEventListener('click', nextSlide);

        const carouselContainer = document.querySelector('.fullscreen-carousel');
        carouselContainer.appendChild(prevBtn);
        carouselContainer.appendChild(nextBtn);
    }

    // Инициализируем карусель
    initCarousel();
    addNavigationButtons();
});