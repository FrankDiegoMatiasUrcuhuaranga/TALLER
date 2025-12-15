<%-- 
    Document    : index.jsp
    DESCRIPCIÓN: Login Final - Estructura Split + Logo UPLA en Formulario + Barra Decorativa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es" class="h-full">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acceso Institucional - UPLA</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Crimson+Text:ital,wght@0,400;0,600;0,700;1,400&family=Inter:wght@300;400;500;600;700&family=Orbitron:wght@500;700&display=swap" rel="stylesheet">
    
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        upla: {
                            blue: '#1e3a8a',   /* Azul Institucional */
                            dark: '#172554',   /* Azul Noche */
                        },
                        codex: {
                            gold: '#ca8a04',      /* Dorado Oscuro (Bordes) */
                            goldlight: '#facc15', /* Dorado Brillante (Acentos) */
                        }
                    },
                    fontFamily: {
                        serif: ['Crimson Text', 'serif'],
                        sans: ['Inter', 'sans-serif'],
                        tech: ['Orbitron', 'sans-serif'],
                    },
                    animation: {
                        'fade-in': 'fadeIn 1s ease-out',
                    },
                    keyframes: {
                        fadeIn: {
                            '0%': { opacity: '0', transform: 'translateY(10px)' },
                            '100%': { opacity: '1', transform: 'translateY(0)' },
                        }
                    }
                }
            }
        }
    </script>
    <style>
        body { font-family: 'Inter', sans-serif; }

        /* Carrusel y Fondo */
        .carousel-container { position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 0; }
        .bg-slide {
            position: absolute; top: 0; left: 0; width: 100%; height: 100%;
            background-size: cover; background-position: center;
            opacity: 0; transition: opacity 2s ease-in-out;
        }
        .bg-slide.active { opacity: 1; }
        .overlay-blue {
            background: linear-gradient(135deg, rgba(30, 58, 138, 0.85), rgba(23, 37, 84, 0.95));
            position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 10;
        }
        #particles-canvas { position: absolute; top: 0; left: 0; width: 100%; height: 100%; z-index: 20; pointer-events: none; }

        /* Estilos Formulario */
        .input-codex { transition: all 0.3s; border: 1px solid #e2e8f0; }
        .input-codex:focus { border-color: #ca8a04; box-shadow: 0 0 0 4px rgba(250, 204, 21, 0.1); outline: none; }

        /* Modal */
        .modal-overlay { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background-color: rgba(23, 37, 84, 0.7); backdrop-filter: blur(4px); display: none; align-items: center; justify-content: center; z-index: 50; animation: fadeIn 0.2s ease; }
        .modal-content { background-color: white; border-radius: 1rem; box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25); width: 90%; max-width: 450px; border-top: 4px solid #ca8a04; animation: slideUp 0.3s ease; }
        @keyframes slideUp { from { transform: translateY(20px); opacity: 0; } to { transform: translateY(0); opacity: 1; } }
        .modal-overlay.active { display: flex; }
    </style>
</head>
<body class="h-full flex overflow-hidden bg-white">

    <div class="hidden lg:flex lg:w-7/12 xl:w-2/3 relative flex-col justify-center text-white bg-slate-900 overflow-hidden">
        <div class="carousel-container">
            <div class="bg-slide active" style="background-image: url('https://upla.edu.pe/web/wp-content/uploads/2025/07/10-INGRESOCAMP-scaled-2560x1280-1-1024x512-1.webp');"></div>
            <div class="bg-slide" style="background-image: url('https://upload.wikimedia.org/wikipedia/commons/e/e2/Taller_de_edici%C3%B3n_en_la_UPLA%2C_Huancayo_01.jpg');"></div>
            <div class="bg-slide" style="background-image: url('https://www.upla.edu.pe/2022/wp-content/uploads/2024/09/UPL07617-scaled.jpg');"></div>
        </div>
        <div class="overlay-blue"></div>
        <canvas id="particles-canvas"></canvas>

        <div class="relative z-30 px-16 xl:px-24 w-full animate-fade-in">
            <div class="absolute top-0 left-16 xl:left-24 -mt-20 flex items-center space-x-3">
                <img src="${pageContext.request.contextPath}/img/uplaAzul.png" class="w-12 h-12 brightness-0 invert opacity-80" alt="Logo UPLA White">
                <span class="text-sm font-bold tracking-[0.2em] opacity-80">UPLA</span>
            </div>
            <div class="space-y-6">
                <h1 class="font-serif text-5xl xl:text-7xl font-bold leading-none tracking-tight">
                    Universidad <br>
                    <span class="text-codex-goldlight italic">Peruana Los Andes</span>
                </h1>
                <div class="flex items-center space-x-3 py-2">
                    <div class="h-1 w-24 bg-white/20 rounded-full"></div>
                    <div class="h-2 w-2 bg-codex-goldlight rounded-full shadow-[0_0_10px_rgba(250,204,21,0.8)]"></div> 
                    <div class="h-1 w-12 bg-white/20 rounded-full"></div>
                </div>
                <div class="space-y-2">
                    <h2 class="text-2xl font-tech text-white uppercase tracking-wider font-bold">Sistema de Grados y Títulos</h2>
                    <p class="text-blue-100 text-lg font-light max-w-xl">Plataforma integral para la gestión académica, seguimiento de investigación y procesos de titulación.</p>
                </div>
            </div>
            <div class="absolute bottom-0 left-16 xl:left-24 -mb-20 text-xs text-blue-200/50 font-mono">CODE X // THESIS MANAGEMENT SYSTEM</div>
        </div>
    </div>

    <div class="w-full lg:w-5/12 xl:w-1/3 flex items-center justify-center p-8 bg-slate-50 relative z-40">
        
        <div class="absolute inset-0 z-0 opacity-40" style="background-image: radial-gradient(#cbd5e1 1px, transparent 1px); background-size: 20px 20px;"></div>

        <div class="w-full max-w-[400px] relative z-10 bg-white/80 backdrop-blur-sm rounded-2xl border border-white shadow-xl overflow-hidden">
            
            <div class="h-2 w-full bg-gradient-to-r from-blue-900 via-yellow-500 to-blue-900"></div>
            
            <div class="p-8">
                <div class="flex flex-col items-center mb-10">
                    <div class="mb-4 relative">
                        <div class="absolute inset-0 bg-codex-goldlight blur-lg opacity-20 rounded-full"></div>
                        <div class="w-16 h-16 bg-upla-blue relative flex items-center justify-center p-3" style="clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);">
                            <img src="${pageContext.request.contextPath}/img/uplaAzul.png" 
                                 alt="Logo UPLA Hexagon" 
                                 class="w-full h-full object-contain brightness-0 invert">
                        </div>
                    </div>
                    
                    <div class="text-center uppercase leading-tight tracking-wide">
                        <h2 class="text-lg font-bold text-slate-600"><span class="text-upla-blue font-extrabold text-xl">U</span>NIVERSIDAD <span class="text-upla-blue font-extrabold text-xl">P</span>ERUANA</h2>
                        <h2 class="text-xl font-extrabold text-slate-600"><span class="text-upla-blue">L</span>OS <span class="text-slate-600">A</span>NDES</h2>
                    </div>
                </div>

                <form id="login-form" class="space-y-6" action="LoginServlet" method="POST">
                    <div class="space-y-1">
                        <label for="email" class="block text-xs font-bold text-slate-500 uppercase tracking-wider ml-1">Usuario</label>
                        <div class="relative">
                            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <svg class="h-5 w-5 text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path></svg>
                            </div>
                            <input id="email" name="email" type="text" placeholder="Digite su Usuario" required class="input-codex w-full pl-10 pr-3 py-3 rounded-lg bg-white text-slate-700 placeholder-slate-400">
                        </div>
                    </div>

                    <div class="space-y-1">
                        <label for="password" class="block text-xs font-bold text-slate-500 uppercase tracking-wider ml-1">Contraseña</label>
                        <div class="relative">
                            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <svg class="h-5 w-5 text-slate-400" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"></path></svg>
                            </div>
                            <input id="password" name="password" type="password" placeholder="Digite su contraseña" required class="input-codex w-full pl-10 pr-10 py-3 rounded-lg bg-white text-slate-700 placeholder-slate-400">
                            <button type="button" id="togglePass" class="absolute inset-y-0 right-0 pr-3 flex items-center text-slate-400 hover:text-codex-gold transition">
                                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path></svg>
                            </button>
                        </div>
                    </div>

                    <div class="flex items-center justify-between text-sm mt-4">
                        <label class="flex items-center cursor-pointer">
                            <input type="checkbox" id="remember" class="h-4 w-4 text-upla-blue border-gray-300 rounded focus:ring-codex-gold">
                            <span class="ml-2 text-slate-600">Recuérdame</span>
                        </label>
                        <button type="button" id="btn-forgot-password" class="text-codex-gold hover:text-yellow-600 font-semibold transition text-xs uppercase tracking-wide">¿Olvidaste tu contraseña?</button>
                    </div>

                    <button type="submit" class="w-full bg-upla-blue hover:bg-upla-dark text-white font-bold py-3.5 px-4 rounded-lg shadow-lg transition duration-300 uppercase tracking-widest text-sm border-b-4 border-slate-900 active:border-b-0 active:translate-y-1">Ingresar</button>
                </form>
                
                <div class="mt-4 h-6">
                   <c:if test="${not empty errorMsg}">
                       <div class="text-center text-red-500 text-xs font-bold bg-red-50 py-1 px-2 rounded border border-red-100 flex items-center justify-center animate-pulse">
                           <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"></path></svg>
                           ${errorMsg}
                       </div>
                   </c:if>
                </div>

                <div class="mt-8 border-t border-slate-100 pt-4 text-center">
                    <p class="text-[10px] text-slate-400 uppercase tracking-widest mb-2 font-bold">Accesos Rápidos (Demo)</p>
                    <div class="flex justify-center gap-2">
                        <button onclick="fillLogin('admin@ms.upla.edu.pe', 'admin123')" class="px-2 py-1 text-[10px] font-bold text-red-600 bg-red-50 rounded border border-red-100 hover:bg-red-100 transition">Admin</button>
                        <button onclick="fillLogin('d.rfernandez@ms.upla.edu.pe', 'raul123')" class="px-2 py-1 text-[10px] font-bold text-blue-600 bg-blue-50 rounded border border-blue-100 hover:bg-blue-100 transition">Docente</button>
                        <button onclick="fillLogin('a.dflores@ms.upla.edu.pe', 'diego123')" class="px-2 py-1 text-[10px] font-bold text-green-600 bg-green-50 rounded border border-green-100 hover:bg-green-100 transition">Estudiante</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="forgot-password-modal" class="modal-overlay">
        <div class="modal-content">
            <form id="forgot-form" class="p-8 space-y-5">
                <div class="flex justify-between items-center mb-2">
                    <h3 class="text-xl font-bold text-slate-800">Recuperar Acceso</h3>
                    <button type="button" id="btn-close-forgot-modal" class="text-slate-400 hover:text-slate-600"><svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg></button>
                </div>
                <p class="text-sm text-slate-500">Ingresa tu correo institucional. Te enviaremos un enlace seguro.</p>
                <div>
                    <label class="block text-xs font-bold text-slate-500 uppercase mb-1">Correo Institucional</label>
                    <input type="email" id="forgot_email" class="w-full border border-slate-300 rounded-lg p-3 text-sm focus:outline-none focus:border-codex-gold focus:ring-1 focus:ring-codex-gold transition" required>
                </div>
                <div id="forgot-message-container"></div>
                <div class="flex justify-end space-x-3 pt-2">
                    <button type="button" id="btn-cancel-forgot-modal" class="px-4 py-2 text-sm font-bold text-slate-600 hover:bg-slate-100 rounded-lg transition">Cancelar</button>
                    <button type="submit" class="px-6 py-2 text-sm font-bold text-white bg-upla-blue hover:bg-upla-dark rounded-lg shadow transition">Enviar</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const slides = document.querySelectorAll('.bg-slide');
            let currentSlide = 0;
            setInterval(() => {
                slides[currentSlide].classList.remove('active');
                currentSlide = (currentSlide + 1) % slides.length;
                slides[currentSlide].classList.add('active');
            }, 6000);

            const canvas = document.getElementById('particles-canvas');
            const ctx = canvas.getContext('2d');
            let width, height, particles = [];
            const particleCount = 70; const connectionDistance = 160;

            function resize() { width = canvas.width = canvas.parentElement.offsetWidth; height = canvas.height = canvas.parentElement.offsetHeight; }
            class Particle {
                constructor() { this.x = Math.random() * width; this.y = Math.random() * height; this.vx = (Math.random() - 0.5) * 0.3; this.vy = (Math.random() - 0.5) * 0.3; this.size = Math.random() * 1.5 + 0.5; }
                update() { this.x += this.vx; this.y += this.vy; if (this.x < 0 || this.x > width) this.vx *= -1; if (this.y < 0 || this.y > height) this.vy *= -1; }
                draw() { ctx.beginPath(); ctx.arc(this.x, this.y, this.size, 0, Math.PI*2); ctx.fillStyle = 'rgba(255, 255, 255, 0.4)'; ctx.fill(); }
            }
            function animate() {
                ctx.clearRect(0, 0, width, height);
                for (let i=0; i<particles.length; i++) {
                    particles[i].update(); particles[i].draw();
                    for (let j=i+1; j<particles.length; j++) {
                        let dx = particles[i].x - particles[j].x, dy = particles[i].y - particles[j].y;
                        let dist = Math.sqrt(dx*dx + dy*dy);
                        if (dist < connectionDistance) { ctx.beginPath(); ctx.strokeStyle = `rgba(250, 204, 21, ${0.15 * (1 - dist/connectionDistance)})`; ctx.lineWidth = 0.5; ctx.moveTo(particles[i].x, particles[i].y); ctx.lineTo(particles[j].x, particles[j].y); ctx.stroke(); }
                    }
                }
                requestAnimationFrame(animate);
            }
            window.addEventListener('resize', resize); resize(); for(let i=0; i<particleCount; i++) particles.push(new Particle()); animate();

            const toggleBtn = document.getElementById('togglePass');
            const passInput = document.getElementById('password');
            toggleBtn.addEventListener('click', () => {
                const type = passInput.type === 'password' ? 'text' : 'password';
                passInput.type = type;
                toggleBtn.classList.toggle('text-codex-gold');
            });

            window.fillLogin = (e, p) => { document.getElementById('email').value = e; document.getElementById('password').value = p; };

            const modal = document.getElementById('forgot-password-modal');
            const openModal = () => modal.classList.add('active');
            const closeModal = () => modal.classList.remove('active');
            
            document.getElementById('btn-forgot-password').addEventListener('click', openModal);
            document.getElementById('btn-close-forgot-modal').addEventListener('click', closeModal);
            document.getElementById('btn-cancel-forgot-modal').addEventListener('click', closeModal);
            modal.addEventListener('click', e => { if(e.target === modal) closeModal(); });

            document.getElementById('forgot-form').addEventListener('submit', (e) => {
                e.preventDefault();
                const msg = document.getElementById('forgot-message-container');
                msg.innerHTML = '<div class="p-3 bg-green-50 text-green-700 text-xs font-bold rounded border border-green-200 mt-2">Enlace enviado correctamente.</div>';
                setTimeout(() => { closeModal(); msg.innerHTML = ''; }, 2000);
            });
        });
    </script>
</body>
</html>