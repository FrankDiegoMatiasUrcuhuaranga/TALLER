<%-- 
    Document    : student.jsp
    DESCRIPCIÓN: Portal Estudiante - Estilo CODEX + Bordes Dorados Unificados + Documentos Completos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %> 

<!DOCTYPE html>
<html lang="es" class="h-full">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CODEX - Portal Estudiante</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&family=Merriweather:wght@300;400;700;900&family=Orbitron:wght@500;700;900&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/lucide@latest"></script>
    
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        codex: {
                            green: '#065f46',   /* Verde UPLA Profundo */
                            greendark: '#064e3b',
                            gold: '#facc15',    /* Dorado Brillante */
                            goldhover: '#eab308',
                            bg: '#f0fdf4'       /* Fondo Verde Muy Claro */
                        },
                        upla: {
                            50: '#ecfdf5', 100: '#d1fae5', 500:'#10b981', 600: '#059669', 700: '#047857', 800: '#065f46', 900: '#064e3b',
                        }
                    },
                    fontFamily: {
                        sans: ['Inter', 'sans-serif'],
                        tech: ['Orbitron', 'sans-serif'],
                        serif: ['Merriweather', 'serif'], /* Fuente para el Acta */
                    }
                }
            }
        }
    </script>
    <style>
        /* --- ESTILOS GENERALES CODEX --- */
        body { font-family: 'Inter', sans-serif; }
        .fade-in { animation: fadeIn 0.5s cubic-bezier(0.4, 0, 0.2, 1); }
        @keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }
        .tech-bg-pattern { background-color: #f0fdf4; background-image: radial-gradient(rgba(6, 95, 70, 0.15) 1px, transparent 1px); background-size: 24px 24px; }
        
        /* --- MODIFICADO: Borde Dorado para todas las tarjetas --- */
        .codex-card {
            background-color: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(8px);
            /* Borde cambiado a dorado */
            border: 1px solid #facc15; 
            box-shadow: 0 4px 6px -1px rgba(6, 95, 70, 0.05);
            border-radius: 1rem;
        }

        .tab-button { transition: all 0.2s ease; border-left: 4px solid transparent; color: #a7f3d0; }
        .tab-button:hover { background-color: rgba(0, 0, 0, 0.2); color: #facc15; padding-left: 1.5rem; }
        .tab-active { background-color: rgba(6, 78, 59, 0.4); color: #facc15 !important; border-left-color: #facc15; font-family: 'Orbitron', sans-serif; letter-spacing: 0.05em; }
        .tab-content { display: none; }
        .tab-content.active { display: block; }
        
        /* Stepper Tech */
        .step-circle { width: 3rem; height: 3rem; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold; z-index: 10; position: relative; transition: all 0.3s; }
        .step-active .step-circle { background-color: #059669; color: white; box-shadow: 0 0 0 4px #d1fae5; border: 2px solid #059669; }
        .step-inactive .step-circle { background-color: #ecfdf5; color: #6ee7b7; border: 2px solid #a7f3d0; }
        .step-current .step-circle { background-color: #064e3b; color: #facc15; border: 2px solid #facc15; animation: pulse-gold 2s infinite; }
        @keyframes pulse-gold { 0% { box-shadow: 0 0 0 0 rgba(250, 204, 21, 0.4); } 70% { box-shadow: 0 0 0 10px rgba(250, 204, 21, 0); } 100% { box-shadow: 0 0 0 0 rgba(250, 204, 21, 0); } }

        /* --- MODALES --- */
        .modal-overlay { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background-color: rgba(6, 78, 59, 0.7); backdrop-filter: blur(4px); display: none; align-items: center; justify-content: center; z-index: 50; animation: fadeIn 0.2s ease-out; }
        .modal-overlay.active { display: flex; }
        
        .modal-content-tech { background-color: white; border-radius: 0.5rem; box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25); width: 90%; max-width: 600px; border-top: 4px solid #facc15; animation: slideUp 0.3s ease; }

        /* --- MODIFICADO: Borde Dorado para Documentos Clásicos --- */
        .classic-paper-doc {
            background-color: #fff !important;
            max-width: 850px !important;
            width: 95%;
            margin: 2rem auto;
            padding: 4rem 3rem !important;
            box-shadow: 0 10px 30px rgba(0,0,0,0.15) !important;
            border-radius: 2px !important;
            /* Agregado borde dorado completo */
            border: 2px solid #facc15 !important; 
            font-family: 'Merriweather', serif !important;
            color: #222 !important;
            line-height: 1.8;
        }
        .classic-paper-doc h1, .classic-paper-doc h2 { font-family: 'Merriweather', serif !important; color: #111 !important; }
        
        /* Círculo de nota clásico */
        .acta-grade-circle-classic { width: 150px; height: 150px; border-radius: 50%; border: 4px solid #222; display: flex; align-items: center; justify-content: center; margin: 2rem auto; }
        .acta-grade-number-classic { font-size: 3.5rem; font-weight: 800; color: #222; }
        .signature-line { border-top: 1px solid #444; padding-top: 0.5rem; font-weight: bold; font-size: 0.8rem; text-transform: uppercase; }


        .circular-chart { display: block; margin: 0 auto; max-width: 80%; max-height: 250px; }
        .circle-bg { fill: none; stroke: #d1fae5; stroke-width: 2.5; }
        .circle { fill: none; stroke-width: 2.5; stroke-linecap: round; animation: progress 1.5s ease-out forwards; }
        @keyframes progress { 0% { stroke-dasharray: 0 100; } }
        .rubric-row:nth-child(even) { background-color: #f0fdf4; }
        .custom-scroll::-webkit-scrollbar { width: 6px; }
        .custom-scroll::-webkit-scrollbar-thumb { background: #10b981; border-radius: 4px; }
        
        /* --- NUEVO: Barra de desplazamiento NEGRA para documentos --- */
.black-scroll::-webkit-scrollbar { width: 8px; }
.black-scroll::-webkit-scrollbar-thumb { background: #000000; border-radius: 4px; }
.black-scroll::-webkit-scrollbar-track { background: transparent; }

        @media print {
            body * { visibility: hidden; }
            .modal-overlay.active, .modal-overlay.active * { visibility: visible; }
            .modal-content { position: absolute; left: 0; top: 0; width: 100%; margin: 0; padding: 0; box-shadow: none; border: none; max-width: 100%; }
            .no-print { display: none !important; }
            .modal-overlay { background: white; position: static; display: block; padding: 0;}
             .classic-paper-doc { box-shadow: none !important; padding: 2rem !important; margin: 0 !important; width: 100% !important; max-width: 100% !important; border: none !important; }
        }
    </style>
</head>
<body class="h-full flex flex-col lg:flex-row overflow-hidden text-slate-800">
    
    <aside class="w-full lg:w-72 bg-codex-green shadow-2xl min-h-full flex-shrink-0 z-30 border-r border-codex-greendark flex flex-col h-full no-print">
        <div class="flex flex-col h-full">
            <div class="text-center py-8 border-b border-codex-greendark bg-codex-greendark/30">
                <div class="relative mx-auto w-20 h-20 mb-4 group cursor-pointer">
                    <div class="absolute inset-0 bg-codex-gold rounded-full blur opacity-20 group-hover:opacity-60 transition duration-500"></div>
                    <div class="relative w-full h-full bg-codex-greendark rounded-full flex items-center justify-center border-2 border-codex-gold text-codex-gold text-2xl font-bold font-tech shadow-inner">
                        ${fn:substring(sessionScope.usuarioLogueado.nombres, 0, 1)}${fn:substring(sessionScope.usuarioLogueado.apellidos, 0, 1)}
                    </div>
                </div>
                <h3 class="text-white font-tech font-bold text-lg tracking-wide uppercase">${sessionScope.usuarioLogueado.getNombreCompleto()}</h3>
                <p class="text-upla-100 text-xs mt-1 font-mono tracking-wider">${sessionScope.usuarioLogueado.codigo}</p>
            </div>
            <nav class="flex-1 py-6 px-0 space-y-1 overflow-y-auto custom-scroll">
                <c:if test="${not empty tesis}">
                    <p class="px-6 text-[10px] font-bold text-codex-gold uppercase tracking-[0.2em] mb-2 mt-2 opacity-90">Académico</p>
                    <button id="btn-evaluacion" class="tab-button tab-inactive w-full text-left px-6 py-4 text-sm font-medium flex items-center group border-b border-white/5"><i data-lucide="book-open" class="w-5 h-5 mr-3"></i> Mi Proyecto</button>
                    <button id="btn-subir" class="tab-button tab-inactive w-full text-left px-6 py-4 text-sm font-medium flex items-center group border-b border-white/5"><i data-lucide="upload-cloud" class="w-5 h-5 mr-3"></i> Subir Avance</button>
                    <button id="btn-historial" class="tab-button tab-inactive w-full text-left px-6 py-4 text-sm font-medium flex items-center group border-b border-white/5"><i data-lucide="history" class="w-5 h-5 mr-3"></i> Historial</button>
                </c:if>
                <p class="px-6 text-[10px] font-bold text-codex-gold uppercase tracking-[0.2em] mb-2 mt-6 opacity-90">Administrativo</p>
                <button id="btn-tramites" class="tab-button tab-inactive w-full text-left px-6 py-4 text-sm font-medium flex items-center group border-b border-white/5"><i data-lucide="folder-check" class="w-5 h-5 mr-3"></i> Carpeta de Titulación</button>
                <button id="btn-estado" class="tab-button tab-inactive w-full text-left px-6 py-4 text-sm font-medium flex items-center group border-b border-white/5"><i data-lucide="award" class="w-5 h-5 mr-3"></i> Estado de Titulación</button>
            </nav>
            <div class="p-4 border-t border-codex-greendark lg:hidden bg-codex-greendark/50">
                <button onclick="logout()" class="w-full bg-codex-greendark hover:bg-black text-white px-4 py-3 rounded-xl text-sm font-semibold flex items-center justify-center transition border border-white/10"><i data-lucide="log-out" class="w-4 h-4 mr-2"></i> Cerrar Sesión</button>
            </div>
        </div>
    </aside>

    <div class="flex-1 flex flex-col h-screen overflow-hidden relative tech-bg-pattern">
        <header class="bg-white/90 backdrop-blur-md border-b border-upla-100 h-24 flex items-center justify-between px-10 shrink-0 shadow-sm z-20 no-print">
            <div><h1 id="main-title" class="text-3xl font-tech font-bold text-upla-900 uppercase tracking-tight">Portal Estudiante</h1><p class="text-sm text-upla-700 font-medium opacity-80 mt-1">Universidad Peruana Los Andes</p></div>
            <div class="flex items-center space-x-6">
                <div class="relative">
                    <button id="btn-notificaciones-toggle" class="relative p-2 text-upla-800 hover:text-codex-gold transition focus:outline-none"><i data-lucide="bell" class="w-7 h-7"></i><c:if test="${not empty notificaciones}"><span class="absolute top-1 right-1 h-3 w-3 bg-codex-gold rounded-full animate-pulse ring-2 ring-white"></span></c:if></button>
                    <div id="dropdown-notificaciones" class="hidden absolute right-0 mt-3 w-80 bg-white border border-upla-100 rounded-lg shadow-xl z-50 overflow-hidden transform transition-all origin-top-right">
                        <div class="bg-upla-50 px-4 py-3 border-b border-upla-100"><p class="text-xs font-bold text-upla-800 uppercase tracking-wide">Notificaciones</p></div>
                        <div class="max-h-64 overflow-y-auto custom-scroll"><c:forEach var="noti" items="${notificaciones}"><div class="p-4 hover:bg-upla-50 border-b border-upla-50 transition group cursor-pointer"><p class="text-sm text-slate-700 font-medium group-hover:text-upla-800 transition-colors">${noti.mensaje}</p><p class="text-xs text-upla-500 mt-1 font-mono"><fmt:formatDate value="${noti.fechaEnvio}" pattern="dd/MM HH:mm"/></p></div></c:forEach><c:if test="${empty notificaciones}"><div class="p-6 text-center text-sm text-upla-400">Sin novedades recientes.</div></c:if></div>
                    </div>
                </div>
                <button id="btn-logout-header" onclick="logout()" class="hidden lg:flex bg-codex-green hover:bg-codex-greendark text-white px-6 py-2.5 rounded-lg text-sm font-bold transition shadow-md items-center border-b-4 border-codex-greendark active:border-b-0 active:translate-y-1"><span class="mr-2">Cerrar Sesión</span><i data-lucide="log-out" class="w-5 h-5"></i></button>
            </div>
        </header>            

        <main class="flex-1 overflow-y-auto px-6 pt-6 pb-10 lg:px-10 lg:pt-8 lg:pb-12 scroll-smooth">
            <div class="mb-6 space-y-2">
                <c:if test="${not empty studentMsg}"><div class="p-4 bg-emerald-50/90 border-l-4 border-emerald-500 text-emerald-800 codex-card shadow-sm flex items-center backdrop-blur-sm"><i data-lucide="check-circle" class="w-5 h-5 mr-3"></i> ${studentMsg}</div></c:if>
                <c:if test="${not empty studentError}"><div class="p-4 bg-red-50/90 border-l-4 border-red-500 text-red-800 codex-card shadow-sm flex items-center backdrop-blur-sm"><i data-lucide="alert-triangle" class="w-5 h-5 mr-3"></i> ${studentError}</div></c:if>
            </div>
            
            <c:if test="${empty tesis}">
                <div id="dashboard-empty" class="tab-content active fade-in max-w-3xl mx-auto mt-10">
                    <div class="codex-card p-12 text-center">
                        <div class="mx-auto w-24 h-24 bg-upla-50 rounded-full flex items-center justify-center mb-6 border-4 border-white shadow-sm"><i data-lucide="graduation-cap" class="w-12 h-12 text-upla-600"></i></div>
                        <h2 class="text-2xl font-tech font-bold text-upla-900 mb-4 uppercase">Bienvenido al Portal</h2>
                        <p class="text-slate-500 mb-8">No tienes un proyecto de tesis activo.</p>
                        <button onclick="showTab('tramites')" class="bg-codex-green hover:bg-codex-greendark text-codex-gold px-8 py-3 rounded hover:text-white font-bold transition shadow-lg border-b-4 border-codex-greendark active:border-b-0 active:translate-y-1">Ir a Carpeta de Titulación</button>
                    </div>
                </div>
            </c:if>
            
            <c:if test="${not empty tesis}">
                <div id="evaluacion" class="tab-content fade-in">
                    <div class="mb-8 codex-card p-6 border-l-4 border-codex-gold">
                        <span class="text-xs font-bold text-codex-gold uppercase tracking-wider bg-codex-greendark px-3 py-1 rounded-full">Proyecto Actual</span>
                        <h2 class="text-2xl font-bold text-upla-900 mt-4 leading-tight max-w-4xl">${tesis.titulo}</h2>
                        <div class="flex items-center mt-4 space-x-6 text-sm text-slate-500 border-t border-upla-100 pt-4">
    <span class="flex items-center font-medium"><i data-lucide="user" class="w-4 h-4 mr-2 text-codex-gold"></i> ${tesis.nombreDocenteRevisor}</span>
    <span class="px-3 py-1 rounded-full text-xs font-bold uppercase tracking-wide shadow-sm ${tesis.estado == 'Aprobado' ? 'bg-emerald-100 text-emerald-700 border border-emerald-200' : 'bg-yellow-100 text-yellow-700 border border-yellow-200'}">${tesis.estado}</span>
    <c:if test="${not empty tesis.nombreDocenteRevisor}">
        <button class="text-blue-600 hover:text-codex-gold hover:underline font-bold text-xs flex items-center btn-ver-res-asesor"
            data-estudiante="${sessionScope.usuarioLogueado.getNombreCompleto()}"
            data-asesor="${tesis.nombreDocenteRevisor}"
            data-titulo="${tesis.titulo}">
            <i data-lucide="file-check" class="w-4 h-4 mr-1"></i> Ver Resolución Asesoría
        </button>
    </c:if>
</div>
                    </div>
                    <div class="grid grid-cols-1 xl:grid-cols-3 gap-8">
                        <div class="xl:col-span-1 space-y-6">
                            <c:if test="${not empty evaluacion}">
                                <div class="codex-card overflow-hidden">
                                    <div class="bg-upla-50/80 p-4 border-b border-upla-100 text-center backdrop-blur-sm"><h3 class="font-bold text-upla-800 font-tech uppercase tracking-wider">Calificación</h3></div>
                                    <div class="p-8 text-center relative">
                                        <div class="mb-6 relative">
                                            <svg viewBox="0 0 36 36" class="circular-chart w-48 h-48 mx-auto transform -rotate-90 drop-shadow-md">
                                                <path class="circle-bg" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" />
                                                <c:set var="percentage" value="${(evaluacion.puntajeTotal / 38.0) * 100}" />
                                                <path class="circle" stroke="${evaluacion.condicion == 'Aprobado' ? '#059669' : (evaluacion.condicion.startsWith('Aprobado') ? '#facc15' : '#ef4444')}" stroke-dasharray="${percentage}, 100" d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831" />
                                            </svg>
                                            <div class="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center">
                                                <span class="text-5xl font-tech font-extrabold text-upla-900 block tracking-tighter"><fmt:formatNumber value="${evaluacion.puntajeTotal}" maxFractionDigits="1"/></span>
                                                <span class="text-xs text-upla-500 font-bold uppercase">de 38 pts</span>
                                            </div>
                                        </div>
                                        <div class="inline-block px-4 py-2 rounded-full bg-upla-50 border-2 border-white shadow-sm mb-6"><p class="text-sm font-bold ${evaluacion.condicion == 'Aprobado' ? 'text-emerald-700' : 'text-red-700'}">${evaluacion.condicion}</p></div>
                                        <div class="space-y-3">
                                            <a href="${pageContext.request.contextPath}/ReporteEvaluacionServlet?id=${evaluacion.idEvaluacion}" class="flex items-center justify-center w-full bg-codex-green hover:bg-codex-greendark text-codex-gold py-3 rounded-lg font-bold transition text-sm shadow-md border-b-4 border-codex-greendark active:border-b-0 active:translate-y-1"><i data-lucide="file-text" class="w-5 h-5 mr-2"></i> Descargar Informe</a>
                                            <button onclick="openModal('modal-rubrica-detalle')" class="flex items-center justify-center w-full bg-white border-2 border-upla-200 text-upla-700 hover:border-codex-gold hover:text-codex-gold py-3 rounded-lg font-bold transition text-sm shadow-sm"><i data-lucide="list" class="w-5 h-5 mr-2"></i> Ver Rúbrica</button>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <c:if test="${empty evaluacion}">
                                <div class="codex-card p-10 text-center">
                                    <div class="w-16 h-16 bg-upla-50 rounded-full flex items-center justify-center mx-auto mb-4 border-4 border-white shadow-sm"><i data-lucide="clock" class="w-8 h-8 text-upla-400"></i></div>
                                    <h3 class="font-bold text-upla-800 font-tech uppercase">Evaluación Pendiente</h3><p class="text-slate-500 text-sm mt-2">Esperando calificación.</p>
                                </div>
                            </c:if>
                        </div>
                        <div class="xl:col-span-2 space-y-6">
                             <c:if test="${not empty evaluacion}">
                                <div class="codex-card p-8">
                                    <h3 class="text-lg font-bold text-upla-900 mb-6 flex items-center font-tech uppercase"><i data-lucide="message-square" class="w-6 h-6 mr-3 text-codex-gold"></i> Comentarios</h3>
                                    <div class="bg-upla-50/80 p-6 rounded-xl border-l-4 border-codex-gold shadow-sm backdrop-blur-sm"><p class="text-slate-700 leading-relaxed font-medium italic">"${evaluacion.comentarios}"</p></div>
                                </div>
                            </c:if>
                            <div class="codex-card p-6 flex items-center justify-between relative overflow-hidden">
                                <div class="absolute -right-10 -top-10 opacity-5"><i data-lucide="file" class="w-32 h-32 text-upla-600"></i></div>
                                <div class="flex items-center relative z-10">
                                    <div class="w-14 h-14 bg-red-50 rounded-xl flex items-center justify-center mr-5 border-2 border-red-100 shadow-sm"><i data-lucide="file-text" class="w-7 h-7 text-red-500"></i></div>
                                    <div><p class="text-sm font-bold text-upla-800 uppercase tracking-wide">Documento Cargado</p><p class="text-xs text-upla-500 truncate max-w-xs mt-1 font-mono bg-upla-50 px-2 py-1 rounded border border-upla-100">${tesis.archivoPath}</p></div>
                                </div>
                                <a href="${pageContext.request.contextPath}/DownloadServlet?file=${tesis.archivoPath}" target="_blank" class="relative z-10 px-6 py-2.5 bg-white border-2 border-upla-200 hover:border-codex-gold text-upla-700 hover:text-codex-gold rounded-lg text-sm font-bold transition shadow-sm">Visualizar</a>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="subir" class="tab-content fade-in">
                      <div class="max-w-2xl mx-auto codex-card p-8">
                        <h2 class="text-xl font-bold text-upla-900 mb-2 font-tech uppercase">Subir Nueva Versión</h2>
                        <form action="${pageContext.request.contextPath}/SubirCorreccionServlet" method="POST" enctype="multipart/form-data" class="space-y-6">
                            <input type="hidden" name="id_tesis" value="${tesis.idTesis}">
                            <div class="border-3 border-dashed border-upla-200 rounded-xl p-10 text-center hover:bg-upla-50/50 hover:border-codex-gold transition relative group bg-white/50">
                                <input type="file" name="tesis_file" accept=".pdf" class="absolute inset-0 w-full h-full opacity-0 cursor-pointer" required>
                                <div class="w-16 h-16 bg-upla-50 text-upla-400 group-hover:text-codex-gold group-hover:bg-yellow-50 rounded-full flex items-center justify-center mx-auto mb-4 transition-colors shadow-sm border-4 border-white"><i data-lucide="upload" class="w-8 h-8"></i></div>
                                <p class="text-base font-bold text-upla-700">Arrastra o haz clic</p><p class="text-xs text-upla-500 mt-2 font-medium bg-upla-100 inline-block px-3 py-1 rounded-full">Solo PDF (Max 15MB)</p>
                            </div>
                            <div><label class="block text-xs font-bold text-upla-500 uppercase mb-2 ml-1">Nota al Asesor</label><textarea name="comentario_alumno" rows="3" class="w-full border-2 border-upla-200 rounded-xl p-3 text-sm focus:ring-0 focus:border-codex-gold outline-none transition bg-white/80"></textarea></div>
                            <button type="submit" class="w-full bg-codex-gold hover:bg-codex-goldhover text-upla-900 py-3.5 rounded-xl font-bold shadow-md transition border-b-4 border-yellow-600 active:border-b-0 active:translate-y-1">Enviar</button>
                        </form>
                    </div>
                </div>
                
                <div id="historial" class="tab-content fade-in">
                     <div class="codex-card overflow-hidden">
                        <div class="px-6 py-4 border-b border-upla-200 bg-upla-50/80 backdrop-blur-sm"><h2 class="font-bold text-upla-700 font-tech uppercase tracking-wider">Historial</h2></div>
                        <table class="min-w-full divide-y divide-upla-200">
                            <thead class="bg-white"><tr><th class="px-6 py-4 text-left text-xs font-bold text-upla-500 uppercase">Fecha</th><th class="px-6 py-4 text-left text-xs font-bold text-upla-500 uppercase">Resultado</th><th class="px-6 py-4 text-left text-xs font-bold text-upla-500 uppercase">Comentarios</th></tr></thead>
                            <tbody class="divide-y divide-upla-200 bg-white/50">
                                <c:forEach var="eval" items="${historial}">
                                    <tr class="hover:bg-upla-50 transition"><td class="px-6 py-4 text-sm text-upla-600 font-mono font-medium"><fmt:formatDate value="${eval.fechaEvaluacion}" pattern="dd/MM/yyyy"/></td><td class="px-6 py-4"><span class="px-3 py-1 text-xs font-bold rounded-full shadow-sm ${eval.condicion == 'Aprobado' ? 'bg-emerald-100 text-emerald-700 border border-emerald-200' : 'bg-orange-100 text-orange-700 border border-orange-200'}">${eval.condicion}</span></td><td class="px-6 py-4 text-sm text-slate-500 truncate max-w-md font-medium">${eval.comentarios}</td></tr>
                                </c:forEach>
                            </tbody>
                        </table>
                     </div>
                </div>
            </c:if>

            <div id="tramites" class="tab-content fade-in">
                 <div class="flex justify-between items-center mb-8">
                    <div><h2 class="text-2xl font-tech font-bold text-upla-900 uppercase">Carpeta Titulación</h2><p class="text-upla-500 text-sm mt-1 font-medium">Seguimiento del expediente.</p></div>
                    <button id="btn-subir-requisito" class="bg-codex-green hover:bg-codex-greendark text-codex-gold px-6 py-2.5 rounded-lg shadow-md font-bold flex items-center text-sm border-b-4 border-codex-greendark active:border-b-0 active:translate-y-1 transition"><i data-lucide="plus-circle" class="w-5 h-5 mr-2"></i> Adjuntar Documento</button>
                </div>
                <div class="codex-card p-10 mb-8 overflow-x-auto">
                     <div class="flex justify-between relative px-4 min-w-[700px]">
                        <div class="absolute top-6 left-0 w-full h-1 bg-upla-200 -z-10 rounded-full">
                             <div class="h-full bg-codex-green rounded-full transition-all duration-1000" style="width: ${tramiteActual.estadoActual == 'Iniciado' ? '0%' : (tramiteActual.estadoActual == 'Revisión de Carpeta' ? '33%' : (tramiteActual.estadoActual == 'Designación de Jurado' ? '66%' : (tramiteActual.estadoActual == 'Sustentación Programada' ? '85%' : (tramiteActual.estadoActual == 'Titulado' ? '100%' : '0%'))))}"></div>
                        </div>
                        <div class="flex flex-col items-center ${tramiteActual != null ? 'step-active' : 'step-inactive'}"><div class="step-circle shadow-sm">1</div><span class="text-xs font-bold mt-3 text-upla-600 uppercase tracking-wider">Iniciado</span></div>
                        <div class="flex flex-col items-center ${tramiteActual.estadoActual == 'Revisión de Carpeta' || tramiteActual.estadoActual == 'Designación de Jurado' || tramiteActual.estadoActual == 'Sustentación Programada' || tramiteActual.estadoActual == 'Titulado' ? 'step-active' : 'step-inactive'}"><div class="step-circle shadow-sm">2</div><span class="text-xs font-bold mt-3 text-upla-600 uppercase tracking-wider">Revisión</span></div>
                        <div class="flex flex-col items-center ${tramiteActual.estadoActual == 'Designación de Jurado' || tramiteActual.estadoActual == 'Apto para Sustentar' || tramiteActual.estadoActual == 'Sustentación Programada' || tramiteActual.estadoActual == 'Titulado' ? 'step-active' : 'step-inactive'}"><div class="step-circle shadow-sm">3</div><span class="text-xs font-bold mt-3 text-upla-600 uppercase tracking-wider">Jurado</span></div>
                        <div class="flex flex-col items-center ${tramiteActual.estadoActual == 'Sustentación Programada' || tramiteActual.estadoActual == 'Titulado' ? 'step-active' : 'step-inactive'}"><div class="step-circle shadow-sm">4</div><span class="text-xs font-bold mt-3 text-upla-600 uppercase tracking-wider">Sustentación</span></div>
                        <div class="flex flex-col items-center ${tramiteActual.estadoActual == 'Titulado' ? 'step-current' : 'step-inactive'}"><div class="step-circle shadow-sm"><i data-lucide="award" class="w-6 h-6"></i></div><span class="text-xs font-bold mt-3 text-upla-600 uppercase tracking-wider">Titulado</span></div>
                    </div>
                </div>
                <div class="codex-card overflow-hidden">
                    <div class="px-6 py-5 bg-upla-50/80 border-b border-upla-200 backdrop-blur-sm"><h3 class="font-bold text-upla-700 font-tech uppercase tracking-wider">Documentación</h3></div>
                    <table class="min-w-full divide-y divide-upla-200">
                        <thead class="bg-white"><tr><th class="px-6 py-4 text-left text-xs font-bold text-upla-500 uppercase tracking-wider">Requisito</th><th class="px-6 py-4 text-left text-xs font-bold text-upla-500 uppercase tracking-wider">Estado</th><th class="px-6 py-4 text-left text-xs font-bold text-upla-500 uppercase tracking-wider">Observaciones</th></tr></thead>
                        <tbody class="divide-y divide-upla-200 bg-white/50">
                            <c:set var="tiposDocs" value="${fn:split('DNI,Bachiller,Certificado_Idiomas,Foto,Constancia_Practicas', ',')}" />
                            <c:forEach var="tipo" items="${tiposDocs}">
                                <c:set var="doc" value="${null}" /><c:forEach var="d" items="${listaDocumentos}"><c:if test="${d.tipoDocumento == tipo}"><c:set var="doc" value="${d}" /></c:if></c:forEach>
                                <tr class="hover:bg-upla-50 transition">
                                    <td class="px-6 py-4 text-sm font-bold text-upla-800">${tipo.replace('_', ' ')}</td>
                                    <td class="px-6 py-4"><span class="px-3 py-1 text-xs font-bold rounded-full shadow-sm border ${doc.estadoValidacion == 'Validado' ? 'bg-emerald-100 text-emerald-700 border-emerald-200' : (doc.estadoValidacion == 'Rechazado' ? 'bg-red-100 text-red-700 border-red-200' : 'bg-upla-100 text-upla-600 border-upla-200')}">${doc != null ? doc.estadoValidacion : 'Pendiente'}</span></td>
                                    <td class="px-6 py-4 text-sm text-red-500 font-medium">${doc.observacionRechazo}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <div id="estado" class="tab-content fade-in">
                <div class="mb-8"><h2 class="text-3xl font-tech font-bold text-upla-900 uppercase">Estado de Titulación</h2><p class="text-gray-500 mt-2">Progreso detallado de tu trámite.</p></div>
                <div class="grid grid-cols-1 gap-6">
                     <div class="codex-card p-6">
                        <h3 class="text-lg font-bold text-upla-900 mb-3 flex items-center font-tech"><i data-lucide="book-open" class="w-5 h-5 mr-2 text-codex-gold"></i> Fase 1: Asesoría</h3>
                        <div class="grid grid-cols-2 gap-4 text-sm">
                            <div><span class="text-gray-500 block uppercase text-xs font-bold">Asesor</span> <span class="font-semibold text-gray-900">${tesis.nombreDocenteRevisor}</span></div>
                            <div><span class="text-gray-500 block uppercase text-xs font-bold">Nota Proyecto</span> <span class="font-bold text-codex-green text-lg">${evaluacion.puntajeTotal != 0.0 ? evaluacion.puntajeTotal : '-'} / 38</span></div>
                        </div>
                    </div>
                     
                    <div class="codex-card p-6">
                        <div class="flex justify-between items-center mb-3">
                             <h3 class="text-lg font-bold text-upla-900 flex items-center font-tech"><i data-lucide="users" class="w-5 h-5 mr-2 text-codex-gold"></i> Fase 2: Jurados</h3>
                             <c:if test="${not empty sustentacion.codigoMiembro1}">
                                <button class="text-upla-600 hover:text-codex-gold hover:underline font-bold text-xs flex items-center btn-ver-res-jurado"
                                    data-estudiante="${sessionScope.usuarioLogueado.getNombreCompleto()}" 
                                    data-m1="${sustentacion.nombreMiembro1}" 
                                    data-m2="${sustentacion.nombreMiembro2}" 
                                    data-m3="${sustentacion.nombreMiembro3}" 
                                    data-sup="${sustentacion.nombreSuplente}" 
                                    data-fecha="<fmt:formatDate value='${sustentacion.fechaHora}' pattern='dd/MM/yyyy HH:mm'/>" 
                                    data-lugar="${sustentacion.lugarEnlace}">
                                    <i data-lucide="file-text" class="w-4 h-4 mr-1"></i> Ver Resolución
                                </button>
                            </c:if>
                        </div>
                         <c:choose>
                            <c:when test="${not empty sustentacion.codigoMiembro1}">
                                <div class="grid grid-cols-3 gap-4 text-center text-sm mt-2">
    <div class="p-3 bg-upla-50 rounded-lg border border-upla-100 flex flex-col items-center justify-center">
        <p class="text-xs text-upla-500 uppercase font-bold">Presidente</p>
        <p class="font-medium text-gray-800 my-1">${sustentacion.nombreMiembro1}</p>
        <span class="text-xs font-bold px-2 py-0.5 rounded mt-1 ${sustentacion.tieneVotoM1 ? 'bg-emerald-100 text-emerald-700 border border-emerald-200' : 'bg-yellow-100 text-yellow-700 border border-yellow-200'}">
            ${sustentacion.tieneVotoM1 ? sustentacion.puntaje1 : 'Pendiente'}
        </span>
    </div>

    <div class="p-3 bg-upla-50 rounded-lg border border-upla-100 flex flex-col items-center justify-center">
        <p class="text-xs text-upla-500 uppercase font-bold">Secretario</p>
        <p class="font-medium text-gray-800 my-1">${sustentacion.nombreMiembro2}</p>
        <span class="text-xs font-bold px-2 py-0.5 rounded mt-1 ${sustentacion.tieneVotoM2 ? 'bg-emerald-100 text-emerald-700 border border-emerald-200' : 'bg-yellow-100 text-yellow-700 border border-yellow-200'}">
            ${sustentacion.tieneVotoM2 ? sustentacion.puntaje2 : 'Pendiente'}
        </span>
    </div>

    <div class="p-3 bg-upla-50 rounded-lg border border-upla-100 flex flex-col items-center justify-center">
        <p class="text-xs text-upla-500 uppercase font-bold">Vocal</p>
        <p class="font-medium text-gray-800 my-1">${sustentacion.nombreMiembro3}</p>
        <span class="text-xs font-bold px-2 py-0.5 rounded mt-1 ${sustentacion.tieneVotoM3 ? 'bg-emerald-100 text-emerald-700 border border-emerald-200' : 'bg-yellow-100 text-yellow-700 border border-yellow-200'}">
            ${sustentacion.tieneVotoM3 ? sustentacion.puntaje3 : 'Pendiente'}
        </span>
    </div>
</div>
                            </c:when>
                            <c:otherwise><p class="text-gray-400 italic text-sm">Jurados aún no designados.</p></c:otherwise>
                        </c:choose>
                     </div>
                     
                     <div class="codex-card p-6 relative overflow-hidden border-l-4 border-codex-gold">
                        <h3 class="text-lg font-bold text-upla-900 mb-3 flex items-center relative z-10 font-tech"><i data-lucide="calendar-clock" class="w-5 h-5 mr-2 text-codex-gold"></i> Fase 3: Sustentación Oral</h3>
                        <c:choose>
                            <c:when test="${not empty sustentacion.lugarEnlace && sustentacion.lugarEnlace != 'Por definir'}">
                                <div class="relative z-10 space-y-4">
                                    <div class="flex items-center gap-4 p-4 bg-upla-50 rounded-xl border border-upla-100">
                                        <div class="text-center px-4 border-r border-upla-200">
                                            <p class="text-sm font-bold text-codex-green uppercase"><fmt:formatDate value="${sustentacion.fechaHora}" pattern="MMMM"/></p>
                                            <p class="text-2xl font-bold text-gray-800"><fmt:formatDate value="${sustentacion.fechaHora}" pattern="dd"/></p>
                                        </div>
                                        <div>
                                            <p class="text-sm font-bold text-gray-700"><i data-lucide="clock" class="w-4 h-4 inline mr-1 text-codex-gold"></i> <fmt:formatDate value="${sustentacion.fechaHora}" pattern="HH:mm"/> hrs</p>
                                            <p class="text-sm text-gray-600 mt-1"><i data-lucide="map-pin" class="w-4 h-4 inline mr-1 text-codex-gold"></i> ${sustentacion.lugarEnlace}</p>
                                        </div>
                                    </div>
                                     <c:if test="${sustentacion.notaFinal > 0}">
                                        <div class="border-t border-upla-100 pt-4 mt-2 flex justify-between items-center">
                                            <span class="text-gray-700 font-bold uppercase text-sm">Promedio Final:</span>
                                            <span class="text-3xl font-tech font-bold text-codex-green">${sustentacion.notaFinal}</span>
                                        </div>
                                    </c:if>
                                    <c:if test="${tramiteActual.estadoActual == 'Titulado'}">
                                        <button onclick="openModal('modal-acta-titulacion')" class="w-full mt-2 bg-codex-green hover:bg-codex-greendark text-white py-3 rounded-xl font-bold shadow-lg transition flex justify-center items-center gap-2 border-b-4 border-codex-greendark active:border-b-0 active:translate-y-1">
                                            <i data-lucide="file-badge" class="w-5 h-5"></i> Ver Acta de Titulación
                                        </button>
                                    </c:if>
                                </div>
                            </c:when>
                            <c:otherwise><p class="text-gray-400 italic text-sm relative z-10">Fecha pendiente de programación.</p></c:otherwise>
                        </c:choose>
                     </div>
                </div>
            </div>

        </main>
    </div>
<!-- Modal Resolución de Asesoría -->
<div id="modal-res-asesor" class="modal-overlay">
    <div class="modal-content classic-paper-doc relative max-h-[90vh] overflow-y-auto black-scroll">
        <button onclick="closeModal('modal-res-asesor')" class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 no-print"><i data-lucide="x" class="w-6 h-6"></i></button>
        
        <div class="p-4 text-justify text-gray-900 font-serif">
            <div class="text-center mb-8 border-b border-gray-300 pb-6">
                <h2 class="text-xl font-bold uppercase tracking-widest text-slate-900">Universidad Peruana Los Andes</h2>
                <p class="text-sm text-gray-500 italic">Facultad de Ingeniería</p>
            </div>
            <div class="text-center font-bold underline mb-8 uppercase">RESOLUCIÓN DE DECANATO N° 234-2025-DFI-UPLA</div>
            
            <div class="space-y-6 leading-relaxed">
                <p><strong>VISTO:</strong> El expediente presentado para la aprobación e inscripción del Plan de Tesis del estudiante.</p>
                
                <p><strong>CONSIDERANDO:</strong> Que el Reglamento General de Grados y Títulos establece la designación de un asesor para guiar la investigación.</p>
                
                <p class="mt-4"><strong>SE RESUELVE:</strong></p>
                
                <p><strong>ARTÍCULO PRIMERO:</strong> DESIGNAR al docente <strong id="ra-asesor" class="uppercase"></strong> como ASESOR del Plan de Tesis titulado: "<span id="ra-titulo" class="italic"></span>", presentado por el bachiller <strong id="ra-estudiante" class="uppercase"></strong>.</p>
                
                <p><strong>ARTÍCULO SEGUNDO:</strong> Encargar a la Coordinación de Grados el cumplimiento de la presente resolución.</p>
                
                <p class="text-center mt-10 font-bold uppercase tracking-wide">REGÍSTRESE, COMUNÍQUESE Y ARCHÍVESE.</p>
            </div>
            
            <div class="mt-24 text-center">
                <div class="border-t border-black w-64 mx-auto pt-2 font-bold">Decano de la Facultad</div>
            </div>
        </div>
        
        <div class="mt-8 flex justify-end no-print">
            <button onclick="window.print()" class="bg-slate-800 text-white px-4 py-2 rounded font-bold flex items-center gap-2"><i data-lucide="printer" class="w-4 h-4"></i> Imprimir</button>
        </div>
    </div>
</div>
    <div id="modal-res-jurado" class="modal-overlay">
        <div class="modal-content classic-paper-doc relative max-h-[90vh] overflow-y-auto black-scroll">
            <button onclick="closeModal('modal-res-jurado')" class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 no-print"><i data-lucide="x" class="w-6 h-6"></i></button>
            
            <div class="p-4 text-justify text-gray-900 font-serif">
                <div class="text-center mb-8 border-b border-gray-300 pb-6">
                    <h2 class="text-xl font-bold uppercase tracking-widest text-slate-900">Universidad Peruana Los Andes</h2>
                    <p class="text-sm text-gray-500 italic">Facultad de Ingeniería</p>
                </div>
                <div class="text-center font-bold underline mb-8 uppercase">RESOLUCIÓN DE DECANATO N° 892-2025-DFI-UPLA</div>
                
                <div class="space-y-6 leading-relaxed">
                    <p><strong>VISTO:</strong> El expediente del bachiller <strong id="rj-estudiante" class="uppercase"></strong>, declarado APTO para la sustentación.</p>
                    
                    <p><strong>SE RESUELVE:</strong></p>
                    
                    <p><strong>ARTÍCULO ÚNICO:</strong> CONFORMAR el Jurado Calificador para la sustentación de la tesis, integrado por:</p>
                    
                    <ul class="list-disc list-inside ml-8 space-y-2">
                        <li><strong>Presidente:</strong> <span id="rj-m1"></span></li>
                        <li><strong>Secretario:</strong> <span id="rj-m2"></span></li>
                        <li><strong>Vocal:</strong> <span id="rj-m3"></span></li>
                        <li><strong>Suplente:</strong> <span id="rj-sup"></span></li>
                    </ul>
                    
                    <p class="mt-6">La sustentación se llevará a cabo el <strong id="rj-fecha"></strong> en <strong id="rj-lugar"></strong>.</p>
                    
                    <p class="text-center mt-10 font-bold uppercase tracking-wide">REGÍSTRESE, COMUNÍQUESE Y ARCHÍVESE.</p>
                </div>
                
                <div class="mt-24 text-center">
                    <div class="border-t border-black w-64 mx-auto pt-2 font-bold">Decano de la Facultad</div>
                </div>
            </div>
            
            <div class="mt-8 flex justify-end no-print">
                <button onclick="window.print()" class="bg-slate-800 text-white px-4 py-2 rounded font-bold flex items-center gap-2"><i data-lucide="printer" class="w-4 h-4"></i> Imprimir</button>
            </div>
        </div>
    </div>


    <div id="modal-acta-titulacion" class="modal-overlay">
        <div class="modal-content classic-paper-doc relative max-h-[90vh] overflow-y-auto custom-scroll">
            <button onclick="closeModal('modal-acta-titulacion')" class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 no-print"><i data-lucide="x" class="w-6 h-6"></i></button>
            
            <div id="printable-acta">
                <div class="text-center mb-8 border-b border-gray-300 pb-6">
                    <h2 class="text-xl font-bold uppercase tracking-widest text-slate-900">Universidad Peruana Los Andes</h2>
                    <p class="text-sm text-gray-500 italic">Facultad de Ingeniería</p>
                </div>
                
                <h1 class="text-2xl font-bold text-center mb-10 uppercase underline decoration-double">ACTA DE SUSTENTACIÓN DE TESIS</h1>
                
                <div class="space-y-6 leading-relaxed text-justify text-gray-900">
                     <p>En la ciudad de Huancayo, a los <span class="font-bold"><fmt:formatDate value="${now}" pattern="dd" /> días del mes de <fmt:formatDate value="${now}" pattern="MMMM" /> del año <fmt:formatDate value="${now}" pattern="yyyy" /></span>, siendo las <fmt:formatDate value="${sustentacion.fechaHora}" pattern="HH:mm" /> horas, se reunieron los miembros del Jurado Calificador:</p>
                     
                     <ul class="list-disc list-inside ml-8 font-medium mt-4 space-y-2">
                        <li><span class="font-bold">Presidente:</span> ${sustentacion.nombreMiembro1}</li>
                        <li><span class="font-bold">Secretario:</span> ${sustentacion.nombreMiembro2}</li>
                        <li><span class="font-bold">Vocal:</span> ${sustentacion.nombreMiembro3}</li>
                     </ul>

                     <p class="mt-6">Para proceder a la calificación de la sustentación de la Tesis titulada:</p>
                     
                     <div class="bg-gray-50 p-6 border-l-4 border-gray-200 my-6 text-center font-bold italic text-gray-900 text-base leading-relaxed shadow-sm">
                        "${tesis.titulo}"
                     </div>
                     
                     <p>Presentada por el Bachiller <span class="font-bold uppercase">${sessionScope.usuarioLogueado.getNombreCompleto()}</span>, con código <span class="font-mono text-sm">${sessionScope.usuarioLogueado.codigo}</span>.</p>

                     <p>Luego de escuchar la exposición y la absolución de las preguntas formuladas, el Jurado procedió a la deliberación y calificación en privado, obteniéndose el siguiente resultado:</p>
                     
                     <div class="text-center my-12">
                        <p class="text-xs uppercase text-gray-500 tracking-widest mb-3 font-bold">NOTA FINAL</p>
                        <div class="acta-grade-circle-classic mx-auto">
                            <span class="acta-grade-number-classic">${sustentacion.notaFinal > 0 ? sustentacion.notaFinal : '---'}</span>
                        </div>
                        <p class="mt-6 text-xl font-bold text-slate-900 uppercase tracking-wide">APROBADO POR UNANIMIDAD</p>
                     </div>

                     <p>En fe de lo cual, se levanta la presente acta, firmando los miembros del jurado y el graduando en señal de conformidad.</p>
                     
                     <div class="mt-24 grid grid-cols-3 gap-12 text-center">
                        <div class="signature-line">Presidente</div>
                        <div class="signature-line">Secretario</div>
                        <div class="signature-line">Vocal</div>
                    </div>
                </div>
            </div>
            <div class="mt-12 flex justify-end no-print">
                <button onclick="window.print()" class="bg-slate-800 hover:bg-slate-900 text-white px-6 py-3 rounded-md font-bold flex items-center gap-2 transition shadow-md">
                    <i data-lucide="printer" class="w-5 h-5"></i> Imprimir Acta
                </button>
            </div>
        </div>
    </div>

    <c:if test="${not empty evaluacion}">
        <div id="modal-rubrica-detalle" class="modal-overlay">
            <div class="modal-content modal-content-tech bg-white flex flex-col h-[90vh]">
                <div class="p-6 border-b border-upla-200 flex justify-between items-center bg-upla-50">
                    <h3 class="text-xl font-bold text-upla-900 font-tech">Detalle Rúbrica</h3>
                    <button onclick="closeModal('modal-rubrica-detalle')" class="text-upla-400 hover:text-upla-700"><i data-lucide="x" class="w-6 h-6"></i></button>
                </div>
                <div class="flex-1 overflow-y-auto p-0">
                    <table class="w-full text-left border-collapse"><thead class="bg-upla-50 sticky top-0 z-10"><tr><th class="p-4 text-xs font-bold uppercase border-b">Criterio</th><th class="p-4 text-center text-xs font-bold uppercase border-b w-24">Ptj</th></tr></thead><tbody class="divide-y divide-upla-100 text-sm"><c:forEach var="i" begin="1" end="38"><tr class="rubric-row"><td class="p-4 text-upla-700">Ítem #${i}</td><td class="p-4 text-center font-mono font-bold text-upla-700 bg-upla-50/50">${evaluacion['item'.concat(i)]}</td></tr></c:forEach></tbody></table>
                </div>
                <div class="p-4 border-t border-upla-200 bg-upla-50 text-right"><button onclick="closeModal('modal-rubrica-detalle')" class="bg-codex-green text-white px-6 py-2 rounded font-bold">Cerrar</button></div>
            </div>
        </div>
    </c:if>
    
    <div id="modal-subir-requisito" class="modal-overlay">
        <div class="modal-content modal-content-tech bg-white p-8 max-w-md">
            <h3 class="text-xl font-bold text-upla-900 mb-6 font-tech uppercase">Adjuntar Requisito</h3>
            <form action="${pageContext.request.contextPath}/TramiteServlet" method="POST" enctype="multipart/form-data" class="space-y-5">
                <input type="hidden" name="id_tramite" value="${tramiteActual != null ? tramiteActual.idTramite : 0}">
                <div><label class="block text-xs font-bold text-upla-500 uppercase mb-2">Tipo</label><select name="tipo_documento" class="w-full border-2 border-upla-200 rounded-xl p-3 text-sm focus:border-codex-gold outline-none"><option value="DNI">Copia de DNI</option><option value="Bachiller">Grado de Bachiller</option><option value="Certificado_Idiomas">Idiomas</option><option value="Foto">Foto Pasaporte</option></select></div>
                <div><label class="block text-xs font-bold text-upla-500 uppercase mb-2">Archivo</label><input type="file" name="archivo_requisito" class="block w-full text-sm text-upla-500 file:bg-upla-100 file:border-0 file:rounded file:px-4 file:py-2 file:text-upla-700 hover:file:bg-codex-gold" required></div>
                <div class="flex justify-end space-x-3 pt-4"><button type="button" onclick="closeModal('modal-subir-requisito')" class="px-5 py-2.5 rounded-xl text-upla-600 font-bold hover:bg-upla-100">Cancelar</button><button type="submit" class="bg-codex-green text-white px-6 py-2.5 rounded-xl font-bold shadow-lg border-b-4 border-codex-greendark active:border-b-0 active:translate-y-1">Subir</button></div>
            </form>
        </div>
    </div>

    <script>
        lucide.createIcons();
        const now = new Date();
        function showTab(tabId) {
            document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active', 'fade-in'));
            document.querySelectorAll('.tab-button').forEach(b => { b.classList.remove('tab-active'); b.classList.add('tab-inactive'); });
            const activeContent = document.getElementById(tabId);
            const activeButton = document.getElementById('btn-' + tabId);
            if (activeContent) activeContent.classList.add('active', 'fade-in');
            if (activeButton) { activeButton.classList.add('tab-active'); activeButton.classList.remove('tab-inactive'); }
        }
        function openModal(id) { document.getElementById(id).classList.add('active'); }
        function closeModal(id) { document.getElementById(id).classList.remove('active'); }
        function logout() { window.location.href = '${pageContext.request.contextPath}/LogoutServlet'; }
        
        const notifBtn = document.getElementById('btn-notificaciones-toggle');
        const notifDropdown = document.getElementById('dropdown-notificaciones');
        if (notifBtn && notifDropdown) {
            notifBtn.addEventListener('click', (e) => { e.stopPropagation(); notifDropdown.classList.toggle('hidden'); });
            document.addEventListener('click', (e) => { if (!notifBtn.contains(e.target) && !notifDropdown.contains(e.target)) notifDropdown.classList.add('hidden'); });
        }

        document.addEventListener('DOMContentLoaded', () => {
            ['evaluacion', 'subir', 'historial', 'tramites', 'estado'].forEach(id => { document.getElementById('btn-' + id)?.addEventListener('click', () => showTab(id)); });
            document.getElementById('btn-subir-requisito')?.addEventListener('click', () => openModal('modal-subir-requisito'));
            document.querySelectorAll('.modal-overlay').forEach(o => o.addEventListener('click', e => { if(e.target === o) closeModal(o.id); }));
            
            // Event delegation para la resolución de jurado
            document.body.addEventListener('click', function(e) {
    // Resolución de Asesoría
    if(e.target.closest('.btn-ver-res-asesor')) {
        const d = e.target.closest('.btn-ver-res-asesor').dataset;
        document.getElementById('ra-estudiante').textContent = d.estudiante;
        document.getElementById('ra-asesor').textContent = d.asesor;
        document.getElementById('ra-titulo').textContent = d.titulo;
        openModal('modal-res-asesor');
    }
    // Resolución de Jurado
    if(e.target.closest('.btn-ver-res-jurado')) {
        const d = e.target.closest('.btn-ver-res-jurado').dataset;
        document.getElementById('rj-estudiante').textContent = d.estudiante;
        document.getElementById('rj-m1').textContent = d.m1;
        document.getElementById('rj-m2').textContent = d.m2;
        document.getElementById('rj-m3').textContent = d.m3;
        document.getElementById('rj-sup').textContent = d.sup;
        document.getElementById('rj-fecha').textContent = d.fecha;
        document.getElementById('rj-lugar').textContent = d.lugar;
        openModal('modal-res-jurado');
    }
});

            const urlParams = new URLSearchParams(window.location.search);
            const tab = urlParams.get('tab');
            if (tab) showTab(tab); else { <c:if test="${empty tesis}">showTab('dashboard-empty');</c:if><c:if test="${not empty tesis}">showTab('evaluacion');</c:if> }
        });
    </script>
</body>
</html>