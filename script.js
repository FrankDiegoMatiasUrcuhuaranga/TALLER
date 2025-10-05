// Crear partículas de ámbar y hojas
function createParticles() {
  const container = document.getElementById('particles');
  const particleTypes = ['🍃', '💎', '🦴', '🌿', '⚱️'];
  
  for (let i = 0; i < 25; i++) {
    const particle = document.createElement('div');
    particle.className = 'particle';
    particle.textContent = particleTypes[Math.floor(Math.random() * particleTypes.length)];
    particle.style.left = Math.random() * 100 + '%';
    particle.style.fontSize = (Math.random() * 15 + 10) + 'px';
    particle.style.animationDelay = Math.random() * 20 + 's';
    particle.style.animationDuration = (Math.random() * 15 + 15) + 's';
    
    if (Math.random() > 0.6) {
      particle.style.filter = 'hue-rotate(40deg)';
    }
    
    container.appendChild(particle);
  }
}

// Crear dinosaurios caminando
function createDinoSilhouettes() {
  const container = document.getElementById('dinos');
  const dinos = ['🦕', '🦖', '🦴', '🗿'];
  
  for (let i = 0; i < 6; i++) {
    const dino = document.createElement('div');
    dino.className = 'dino-shadow';
    dino.textContent = dinos[Math.floor(Math.random() * dinos.length)];
    dino.style.left = Math.random() * 100 + '%';
    dino.style.animationDelay = Math.random() * 30 + 's';
    dino.style.animationDuration = (Math.random() * 20 + 25) + 's';
    container.appendChild(dino);
  }
}

// Scroll suave hacia arriba
function scrollToTop() {
  window.scrollTo({
    top: 0,
    behavior: 'smooth'
  });
}

// Easter egg jurásico
function showEasterEgg() {
  const messages = [
    "🦕 ¡Has descubierto un T-Rex programador!",
    "🦴 ¡Encontraste un fósil de código JavaScript!",
    "⚱️ ¡Has desenterrado una función prehistórica!",
    "💎 ¡Descubriste código preservado en ámbar digital!",
    "🗿 ¡Has hallado las tablas de piedra del CSS!",
    "🌿 ¡Encontraste un helecho con propiedades de CSS Grid!",
    "🦖 ¡Un Velociraptor te enseñó algoritmos de ordenamiento!"
  ];
  
  const randomMessage = messages[Math.floor(Math.random() * messages.length)];
  
  const notification = document.createElement('div');
  notification.style.cssText = `
    position: fixed;
    top: 20px;
    right: 20px;
    background: linear-gradient(135deg, #4ae287, #d4af37);
    color: white;
    padding: 1.2rem 2rem;
    border-radius: 20px;
    font-weight: bold;
    box-shadow: 0 15px 40px rgba(74, 226, 135, 0.4);
    z-index: 10000;
    transform: translateX(400px);
    transition: transform 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.275);
    border: 2px solid rgba(255, 255, 255, 0.3);
  `;
  notification.textContent = randomMessage;
  
  document.body.appendChild(notification);
  
  setTimeout(() => {
    notification.style.transform = 'translateX(0)';
  }, 100);
  
  setTimeout(() => {
    notification.style.transform = 'translateX(400px)';
    setTimeout(() => {
      document.body.removeChild(notification);
    }, 500);
  }, 3500);
}

// Efecto parallax en scroll
window.addEventListener('scroll', () => {
  const scrolled = window.pageYOffset;
  const particles = document.getElementById('particles');
  const dinos = document.getElementById('dinos');
  
  if (particles) {
    particles.style.transform = `translateY(${scrolled * 0.5}px)`;
  }
  
  if (dinos) {
    dinos.style.transform = `translateY(${scrolled * 0.3}px)`;
  }
});

// Efecto de cursor con partículas
let lastCursorTime = 0;
document.addEventListener('mousemove', (e) => {
  const now = Date.now();
  if (now - lastCursorTime < 50) return;
  lastCursorTime = now;
  
  const isOverCard = e.target.closest('.prehistoric-card');
  
  const particle = document.createElement('div');
  particle.style.cssText = `
    position: fixed;
    left: ${e.clientX}px;
    top: ${e.clientY}px;
    width: ${isOverCard ? '6px' : '3px'};
    height: ${isOverCard ? '6px' : '3px'};
    background: ${isOverCard ? '#d4af37' : '#4ae287'};
    border-radius: 50%;
    pointer-events: none;
    z-index: 9999;
    animation: cursorTrail 1.5s ease-out forwards;
    box-shadow: 0 0 ${isOverCard ? '12px' : '6px'} ${isOverCard ? '#d4af37' : '#4ae287'};
  `;
  
  document.body.appendChild(particle);
  
  setTimeout(() => {
    if (document.body.contains(particle)) {
      document.body.removeChild(particle);
    }
  }, 1500);
});

// Animaciones de entrada
document.addEventListener('DOMContentLoaded', () => {
  createParticles();
  createDinoSilhouettes();
  
  const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        entry.target.style.opacity = '1';
        entry.target.style.transform = 'translateY(0)';
      }
    });
  }, {
    threshold: 0.1
  });

  document.querySelectorAll('.prehistoric-card').forEach((card, index) => {
    card.style.opacity = '0';
    card.style.transform = 'translateY(50px)';
    card.style.transition = `all 0.8s cubic-bezier(0.175, 0.885, 0.32, 1.275) ${index * 0.15}s`;
    observer.observe(card);
  });

  console.log(`
    🦕 ¡Bienvenido al Laboratorio Jurásico del Código! 🦖
    ════════════════════════════════════════════════════
    Has entrado a un mundo donde el desarrollo de software
    se encuentra con la paleontología digital.
    
    Explora con cuidado... los bugs aquí tienen dientes.
    ════════════════════════════════════════════════════
  `);
});

// Vibración en dispositivos móviles
document.querySelectorAll('.prehistoric-card').forEach(card => {
  card.addEventListener('mouseenter', () => {
    if (navigator.vibrate) {
      navigator.vibrate(50);
    }
  });
});