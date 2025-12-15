<%-- 
    Document    : teacher.jsp
    DESCRIPCIÓN: Portal del Docente - Completo con Estilo CODEX (Vino & Dorado)
    ACTUALIZADO: Funcionalidad completa + Estética CODEX
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %> 
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="es" class="h-full">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal del Docente - UPLA</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800&family=Merriweather:wght@400;700&family=Orbitron:wght@500;700;900&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/lucide@latest"></script>
    
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        codex: {
                            sidebar: '#881337',
                            darker: '#4c0519',
                            gold: '#facc15',
                            goldhover: '#eab308',
                            bg: '#fff1f2',
                            text: '#881337'
                        },
                        vino: {
                            50: '#fff1f2',
                            100: '#ffe4e6',
                            200: '#fecdd3',
                            600: '#e11d48',
                            800: '#9f1239',
                            900: '#881337',
                        }
                    },
                    fontFamily: {
                        sans: ['Inter', 'sans-serif'],
                        tech: ['Orbitron', 'sans-serif'],
                        serif: ['Merriweather', 'serif']
                    }
                }
            }
        }
    </script>
    
    <style>
        body { font-family: 'Inter', sans-serif; }
        .fade-in { animation: fadeIn 0.5s cubic-bezier(0.4, 0, 0.2, 1); }
        @keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }
        
        .tech-bg-pattern {
            background-color: #fff1f2;
            background-image: radial-gradient(rgba(136, 19, 55, 0.1) 1px, transparent 1px);
            background-size: 24px 24px;
        }

        /* Navegación Sidebar */
        .tab-button { transition: all 0.3s ease; border-left: 4px solid transparent; cursor: pointer; color: #fecdd3; }
        .tab-active { 
            background-color: rgba(0, 0, 0, 0.3); 
            color: #facc15 !important; 
            border-left-color: #facc15; 
            font-family: 'Orbitron', sans-serif; 
            letter-spacing: 0.05em;
        }
        .tab-inactive:hover { background-color: rgba(0, 0, 0, 0.2); color: #facc15; padding-left: 1.5rem; }
        
        .tab-content { display: none; }
        .tab-content.active { display: block; }

        /* Modales Generales */
        .modal-overlay { position: fixed; top: 0; left: 0; right: 0; bottom: 0; background-color: rgba(76, 5, 25, 0.6); backdrop-filter: blur(4px); display: none; align-items: center; justify-content: center; z-index: 50; animation: fadeIn 0.2s ease-out; }
        .modal-content { background-color: white; border-radius: 1rem; width: 90%; max-width: 600px; max-height: 90vh; animation: slideUp 0.3s ease-out; display: flex; flex-direction: column; border-top: 4px solid #facc15; box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.25); }
        .modal-content-xl { max-width: 1100px; width: 98%; height: 92vh; }
        .modal-overlay.active { display: flex; }
        @keyframes slideUp { from { transform: translateY(20px); opacity: 0; } to { transform: translateY(0); opacity: 1; } }
        
        /* Estilos Documento Oficial (Resolución) */
        .modal-document { max-width: 800px; background: white; padding: 0; font-family: 'Merriweather', serif; color: #000; display: block; overflow-y: auto; }
        .doc-header { text-align: center; border-bottom: 1px solid #ccc; padding-bottom: 1rem; margin-bottom: 2rem; }
        .doc-body { text-align: justify; line-height: 1.6; }
        .doc-title { text-align: center; font-weight: bold; text-decoration: underline; margin: 1.5rem 0; text-transform: uppercase; }
        .doc-section { margin-top: 1rem; margin-bottom: 0.5rem; font-weight: bold; text-transform: uppercase; font-size: 0.9rem; }
        
        /* Impresión */
        @media print {
            body * { visibility: hidden; }
            .modal-overlay.active, .modal-overlay.active * { visibility: visible; }
            .modal-content { position: absolute; left: 0; top: 0; width: 100%; margin: 0; padding: 2cm; box-shadow: none; border: none; max-width: 100%; max-height: none; overflow: visible; }
            .no-print { display: none !important; }
            .modal-overlay { background: white; position: static; display: block; }
        }

        /* Rúbrica */
        .rubrica-table { width: 100%; border-collapse: collapse; font-size: 0.8125rem; }
        .rubrica-table th { background-color: #fff1f2; position: sticky; top: 0; z-index: 10; padding: 0.75rem; text-align: center; font-weight: 700; color: #881337; border-bottom: 2px solid #fecdd3; text-transform: uppercase; letter-spacing: 0.05em; }
        .rubrica-table th:nth-child(2) { text-align: left; }
        .rubrica-table td { padding: 0.5rem 0.75rem; border-bottom: 1px solid #fff1f2; color: #334155; vertical-align: middle; }
        .rubrica-section-row td { background-color: #ffe4e6; color: #881337; font-weight: 800; text-transform: uppercase; font-size: 0.75rem; padding: 0.75rem 1rem; letter-spacing: 0.05em; border-top: 1px solid #fecdd3; }
        .rubrica-row:hover { background-color: #fff0f1; }
        
        /* Radio Buttons Custom */
        .radio-score { cursor: pointer; width: 1.25rem; height: 1.25rem; accent-color: #881337; }
        .score-label { display: flex; width: 100%; height: 100%; justify-content: center; align-items: center; cursor: pointer; transition: background 0.2s; border-radius: 4px; }
        .score-label:hover { background-color: #fff1f2; }

        /* Scrollbar */
        .custom-scroll::-webkit-scrollbar { width: 8px; height: 8px; }
        .custom-scroll::-webkit-scrollbar-track { background: #fff1f2; }
        .custom-scroll::-webkit-scrollbar-thumb { background: #fda4af; border-radius: 4px; }
        .custom-scroll::-webkit-scrollbar-thumb:hover { background: #fb7185; }
        
        /* Utilidad Gradiente */
        .bg-gradient-primary { background: linear-gradient(to right, #881337, #e11d48); }
        .bg-gradient-jury { background: linear-gradient(to right, #7e22ce, #be185d); }
    </style>
</head>
<body class="h-full flex flex-col lg:flex-row overflow-hidden tech-bg-pattern text-slate-800">
    
    <!-- Sidebar -->
    <aside class="w-full lg:w-72 bg-codex-sidebar shadow-2xl flex-shrink-0 text-white z-30 flex flex-col h-full border-r border-codex-darker no-print">
        <div class="flex flex-col h-full">
            
            <div class="text-center py-8 px-6 border-b border-rose-900 bg-codex-darker shrink-0">
                <div class="relative mx-auto w-24 h-24 mb-4 group cursor-pointer">
                    <div class="absolute inset-0 bg-codex-gold rounded-full blur opacity-20 group-hover:opacity-60 transition duration-500"></div>
                    <div class="relative w-full h-full bg-[#4c0519] rounded-full flex items-center justify-center border-2 border-codex-gold text-codex-gold text-3xl font-bold font-tech shadow-inner">
                        ${fn:substring(sessionScope.usuarioLogueado.nombres, 0, 1)}${fn:substring(sessionScope.usuarioLogueado.apellidos, 0, 1)}
                    </div>
                </div>
                <h3 class="text-white font-tech font-bold text-lg tracking-wide uppercase leading-tight">${sessionScope.usuarioLogueado.getNombreCompleto()}</h3>
                <span class="inline-flex items-center px-4 py-1 rounded-full text-[10px] font-bold bg-[#881337] text-codex-gold border border-codex-gold/50 mt-2 uppercase tracking-wider shadow-sm">
                    Docente Investigador
                </span>
            </div>

            <div class="flex-1 overflow-y-auto custom-scroll p-6">
                <nav class="space-y-4">
                    <div id="btn-dashboard" class="tab-button tab-active w-full text-left px-6 py-4 text-base font-medium flex items-center group gap-4 rounded-lg">
                        <i data-lucide="layout-dashboard" class="w-6 h-6 opacity-90"></i> Dashboard
                    </div>

                    <p class="px-6 mt-8 mb-4 text-xs font-bold text-codex-gold uppercase tracking-[0.2em] opacity-80">Supervisión</p>
                    
                    <div id="btn-tesis-asignadas" class="tab-button tab-inactive w-full text-left px-6 py-4 text-base font-medium flex items-center group gap-4 rounded-lg">
                        <i data-lucide="book-open" class="w-6 h-6 opacity-70 group-hover:opacity-100"></i> Tesis Asignadas
                    </div>
                    <div id="btn-mis-estudiantes" class="tab-button tab-inactive w-full text-left px-6 py-4 text-base font-medium flex items-center group gap-4 rounded-lg">
                        <i data-lucide="users" class="w-6 h-6 opacity-70 group-hover:opacity-100"></i> Mis Estudiantes
                    </div>
                    
                    <p class="px-6 mt-8 mb-4 text-xs font-bold text-codex-gold uppercase tracking-[0.2em] opacity-80">Jurado</p>
                    
                    <div id="btn-jurado" class="tab-button tab-inactive w-full text-left px-6 py-4 text-base font-medium flex items-center group gap-4 rounded-lg">
                        <i data-lucide="gavel" class="w-6 h-6 opacity-70 group-hover:opacity-100"></i> Sustentaciones
                    </div>
                    <div id="btn-reportes" class="tab-button tab-inactive w-full text-left px-6 py-4 text-base font-medium flex items-center group gap-4 rounded-lg">
                        <i data-lucide="file-bar-chart" class="w-6 h-6 opacity-70 group-hover:opacity-100"></i> Reportes
                    </div>
                </nav>
            </div>
            
            <div class="mt-auto p-6 border-t border-rose-900 lg:hidden shrink-0">
                <button id="btn-logout-sidebar" class="w-full bg-codex-darker hover:bg-black text-white px-6 py-3 rounded-xl text-base font-medium transition flex items-center justify-center gap-2">
                    <i data-lucide="log-out" class="w-5 h-5"></i> Cerrar Sesión
                </button>
            </div>
        </div>
    </aside>

    <!-- Contenido Principal -->
    <div class="flex-1 flex flex-col h-screen overflow-hidden relative">
        
        <!-- Header -->
        <header class="bg-white/90 backdrop-blur-md border-b border-vino-200 h-16 flex items-center justify-between px-8 shrink-0 shadow-sm z-20 no-print">
            <div>
                <h1 id="header-title" class="text-xl font-tech font-bold text-codex-text uppercase tracking-tight">Dashboard</h1>
            </div>
            <div class="flex items-center space-x-4">
                <!-- Notificaciones -->
                <div class="relative">
                    <button id="btn-notificaciones-toggle" class="p-2 hover:bg-vino-50 rounded-full transition text-codex-sidebar hover:text-codex-text relative focus:outline-none">
                        <i data-lucide="bell" class="w-6 h-6"></i>
                        <c:if test="${not empty notificaciones}"><span class="absolute top-1 right-1 h-2.5 w-2.5 bg-red-500 rounded-full ring-2 ring-white animate-pulse"></span></c:if>
                    </button>
                    <div id="dropdown-notificaciones" class="hidden absolute right-0 mt-2 w-80 bg-white border border-vino-200 rounded-2xl shadow-xl z-50 overflow-hidden transform transition-all duration-200 origin-top-right">
                        <div class="bg-vino-50 px-4 py-3 border-b border-vino-200"><p class="text-xs font-bold text-codex-text uppercase">Notificaciones</p></div>
                        <div class="max-h-64 overflow-y-auto custom-scroll">
                            <c:forEach var="noti" items="${notificaciones}">
                                <div class="p-4 hover:bg-vino-50 border-b border-gray-50 transition"><p class="text-sm text-slate-800 font-medium">${noti.mensaje}</p><p class="text-xs text-slate-400 mt-1"><fmt:formatDate value="${noti.fechaEnvio}" pattern="dd MMM, HH:mm"/></p></div>
                            </c:forEach>
                            <c:if test="${empty notificaciones}"><div class="p-6 text-center text-sm text-gray-400">Sin novedades.</div></c:if>
                        </div>
                    </div>
                </div>
                <div class="h-8 w-px bg-vino-200"></div>
                <button id="btn-logout-header" class="hidden lg:flex bg-codex-sidebar hover:bg-codex-darker text-codex-gold px-5 py-2 rounded-lg text-sm font-bold transition shadow-md items-center gap-2 border-b-4 border-codex-darker active:border-b-0 active:translate-y-1">
                    <i data-lucide="log-out" class="w-4 h-4"></i> Cerrar Sesión
                </button>
            </div>
        </header>

        <main class="flex-1 overflow-y-auto p-8 scroll-smooth">
            
            <!-- 1. DASHBOARD -->
            <div id="dashboard" class="tab-content active fade-in">
                <div class="bg-gradient-to-r from-codex-sidebar to-rose-700 rounded-2xl p-8 mb-8 text-white shadow-lg relative overflow-hidden border border-rose-600">
                    <div class="relative z-10"><h2 class="text-3xl font-bold mb-2 font-tech">¡Bienvenido, ${sessionScope.usuarioLogueado.nombres}!</h2><p class="text-vino-100 text-lg">Gestión de Tesis y Evaluaciones.</p></div>
                    <div class="absolute right-0 top-0 h-full w-1/2 bg-white/10 transform skew-x-12 blur-3xl"></div>
                </div>
                
                <!-- Stats Cards -->
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
                    <!-- Asesor -->
                    <div class="bg-white/95 backdrop-filter backdrop-blur-sm p-6 rounded-xl shadow-sm border border-vino-200 hover:border-codex-gold transition">
                        <p class="text-xs font-bold text-vino-800 uppercase tracking-wide mb-1">Asesor: Pendientes</p>
                        <h3 class="text-3xl font-tech font-bold text-codex-sidebar">${asesorPendientes}</h3>
                    </div>
                    <div class="bg-white/95 backdrop-filter backdrop-blur-sm p-6 rounded-xl shadow-sm border border-vino-200 hover:border-green-300 transition">
                        <p class="text-xs font-bold text-green-600 uppercase tracking-wide mb-1">Asesor: Aprobadas</p>
                        <h3 class="text-3xl font-tech font-bold text-slate-800">${asesorAprobadas}</h3>
                    </div>
                    <!-- Jurado -->
                    <div class="bg-white/95 backdrop-filter backdrop-blur-sm p-6 rounded-xl shadow-sm border border-purple-100 hover:border-purple-300 transition">
                        <p class="text-xs font-bold text-purple-500 uppercase tracking-wide mb-1">Jurado: Por Votar</p>
                        <h3 class="text-3xl font-tech font-bold text-slate-800">${juradoPendientes}</h3>
                    </div>
                    <div class="bg-white/95 backdrop-filter backdrop-blur-sm p-6 rounded-xl shadow-sm border border-purple-100 hover:border-purple-300 transition">
                        <p class="text-xs font-bold text-slate-500 uppercase tracking-wide mb-1">Jurado: Votadas</p>
                        <h3 class="text-3xl font-tech font-bold text-slate-800">${juradoEvaluadas}</h3>
                    </div>
                </div>

                <!-- ACCIONES RÁPIDAS -->
                <div class="bg-white/95 backdrop-filter backdrop-blur-sm rounded-xl shadow-sm border border-vino-200 p-6 mb-8">
                    <h3 class="text-lg font-bold text-codex-text mb-4 font-tech uppercase">Accesos Directos</h3>
                    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                        <button onclick="showTab('tesis-asignadas')" class="p-4 border border-vino-200 rounded-xl hover:border-codex-gold hover:shadow-md transition text-left group bg-vino-50">
                            <i data-lucide="book-open" class="w-6 h-6 text-codex-sidebar mb-2 group-hover:scale-110 transition-transform"></i>
                            <span class="block font-bold text-slate-800">Revisar Tesis</span>
                            <span class="text-xs text-slate-500">Como Asesor</span>
                        </button>
                        <button onclick="showTab('mis-estudiantes')" class="p-4 border border-vino-200 rounded-xl hover:border-codex-gold hover:shadow-md transition text-left group bg-vino-50">
                            <i data-lucide="users" class="w-6 h-6 text-indigo-500 mb-2 group-hover:scale-110 transition-transform"></i>
                            <span class="block font-bold text-slate-800">Mis Estudiantes</span>
                            <span class="text-xs text-slate-500">Historial y Detalles</span>
                        </button>
                        <button onclick="showTab('jurado')" class="p-4 border border-vino-200 rounded-xl hover:border-codex-gold hover:shadow-md transition text-left group bg-vino-50">
                            <i data-lucide="gavel" class="w-6 h-6 text-purple-500 mb-2 group-hover:scale-110 transition-transform"></i>
                            <span class="block font-bold text-slate-800">Sustentaciones</span>
                            <span class="text-xs text-slate-500">Como Jurado</span>
                        </button>
                    </div>
                </div>
                
                <!-- Alertas / Atención Requerida -->
                <h3 class="text-xl font-bold text-codex-text mb-4 font-tech">Atención Requerida</h3>
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
                    <!-- TESIS DE ASESOR PENDIENTES -->
                    <div class="bg-white/95 backdrop-filter backdrop-blur-sm rounded-2xl shadow-sm border border-vino-200 overflow-hidden flex flex-col">
                        <div class="px-6 py-4 border-b border-vino-100 bg-vino-50 flex justify-between items-center"><h4 class="font-bold text-codex-text flex items-center"><i data-lucide="file-edit" class="w-5 h-5 mr-2"></i> Tesis por Revisar (Asesor)</h4></div>
                        <div class="divide-y divide-vino-50 flex-1 overflow-y-auto max-h-96 custom-scroll">
                            <c:set var="countAsesor" value="0"/>
                            <c:forEach var="tesis" items="${listaTesis}">
                                <c:if test="${tesis.estado == 'Pendiente de Revisión' || tesis.estado == 'Revisión Solicitada' || tesis.estado == 'En Proceso'}">
                                    <c:set var="countAsesor" value="${countAsesor + 1}"/>
                                    <div class="p-4 hover:bg-vino-50 transition flex items-center justify-between group">
                                        <div><h5 class="text-sm font-bold text-slate-800 group-hover:text-codex-sidebar transition line-clamp-1">${tesis.titulo}</h5><p class="text-xs text-slate-500 mt-1">Est: ${tesis.nombreEstudiante}</p></div>
                                        <button class="btn-trigger-eval bg-vino-100 text-codex-sidebar hover:bg-codex-sidebar hover:text-codex-gold px-3 py-1.5 rounded-lg text-xs font-bold transition" data-tesis-title="${tesis.titulo}" data-tesis-id="${tesis.idTesis}" data-archivo-path="${tesis.archivoPath}">Evaluar</button>
                                    </div>
                                </c:if>
                            </c:forEach>
                            <c:if test="${countAsesor == 0}"><div class="p-8 text-center text-slate-400 italic text-sm">No tienes tesis pendientes de revisión.</div></c:if>
                        </div>
                    </div>
                    <!-- SUSTENTACIONES DE JURADO PENDIENTES -->
                    <div class="bg-white/95 backdrop-filter backdrop-blur-sm rounded-2xl shadow-sm border border-purple-100 overflow-hidden flex flex-col">
                        <div class="px-6 py-4 border-b border-purple-50 bg-purple-50/40 flex justify-between items-center"><h4 class="font-bold text-purple-800 flex items-center"><i data-lucide="award" class="w-5 h-5 mr-2"></i> Defensas por Calificar (Jurado)</h4></div>
                        <div class="divide-y divide-purple-50 flex-1 overflow-y-auto max-h-96 custom-scroll">
                            <c:set var="countJurado" value="0"/>
                            <c:forEach var="sus" items="${listaSustentaciones}">
                                <c:if test="${sus.estadoEvaluacionJurado == 'Pendiente' && sus.codigoSuplente != sessionScope.usuarioLogueado.codigo}">
                                    <c:set var="countJurado" value="${countJurado + 1}"/>
                                    <div class="p-4 hover:bg-purple-50/60 transition flex items-center justify-between group">
                                        <div><h5 class="text-sm font-bold text-slate-800 group-hover:text-purple-700 transition">${sus.nombreEstudiante}</h5><p class="text-xs text-slate-500 mt-1">
                                            <c:choose>
                                                <c:when test="${not empty sus.lugarEnlace && sus.lugarEnlace != 'Por definir'}"><fmt:formatDate value="${sus.fechaHora}" pattern="dd/MM/yyyy HH:mm"/> • ${sus.lugarEnlace}</c:when>
                                                <c:otherwise>Fecha por definir</c:otherwise>
                                            </c:choose>
                                        </p></div>
                                        <button class="btn-trigger-jurado-eval bg-purple-100 text-purple-700 hover:bg-purple-600 hover:text-white px-3 py-1.5 rounded-lg text-xs font-bold transition" data-sustentacion-id="${sus.idSustentacion}" data-estudiante="${sus.nombreEstudiante}" data-titulo="${sus.tituloTesis}">Calificar</button>
                                    </div>
                                </c:if>
                            </c:forEach>
                            <c:if test="${countJurado == 0}"><div class="p-8 text-center text-slate-400 italic text-sm">No tienes sustentaciones pendientes de voto.</div></c:if>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 2. TESIS ASIGNADAS -->
            <div id="tesis-asignadas" class="tab-content fade-in">
                <h2 class="text-xl font-bold text-codex-text mb-6 font-tech uppercase">Tesis Asignadas (Asesoría)</h2>
                <div class="bg-white/95 backdrop-filter backdrop-blur-sm rounded-2xl shadow-sm border border-vino-200 overflow-hidden">
                    <table class="min-w-full divide-y divide-vino-100">
                        <thead class="bg-vino-50"><tr><th class="px-6 py-4 text-left text-xs font-bold text-vino-800 uppercase">Título</th><th class="px-6 py-4 text-left text-xs font-bold text-vino-800 uppercase">Estudiante</th><th class="px-6 py-4 text-left text-xs font-bold text-vino-800 uppercase">Estado</th><th class="px-6 py-4 text-left text-xs font-bold text-vino-800 uppercase">Documentos</th><th class="px-6 py-4 text-left text-xs font-bold text-vino-800 uppercase">Acción</th></tr></thead>
                        <tbody class="divide-y divide-vino-50">
                            <c:forEach var="tesis" items="${listaTesis}">
                                <tr class="hover:bg-vino-50 transition">
                                    <td class="px-6 py-4 text-sm text-slate-900 font-medium">${tesis.titulo}</td>
                                    <td class="px-6 py-4 text-sm text-slate-500">${tesis.nombreEstudiante}</td>
                                    <td class="px-6 py-4"><span class="px-3 py-1 rounded-full text-xs font-bold ${tesis.estado == 'Aprobado' ? 'bg-green-100 text-green-700' : 'bg-blue-100 text-blue-700'}">${tesis.estado}</span></td>
                                    
                                    <td class="px-6 py-4 flex flex-col gap-2">
                                        <div class="flex items-center gap-2">
                                            <c:if test="${not empty tesis.archivoPath}">
                                                <a href="${pageContext.request.contextPath}/DownloadServlet?file=${tesis.archivoPath}" target="_blank" class="text-xs bg-vino-50 text-codex-sidebar px-3 py-1 rounded border border-vino-200 hover:bg-vino-100 font-bold flex items-center justify-center w-20">
                                                    <i data-lucide="file-text" class="w-3 h-3 mr-1"></i> PDF
                                                </a>
                                            </c:if>
                                            <c:if test="${empty tesis.archivoPath}"><span class="text-xs text-slate-400 italic">Pendiente</span></c:if>
                                            
                                            <button class="btn-ver-res-asesor text-purple-600 hover:text-purple-800 text-xs font-bold flex items-center justify-center bg-purple-50 border border-purple-200 px-3 py-1 rounded transition hover:bg-purple-100" 
                                                data-estudiante="${tesis.nombreEstudiante}" 
                                                data-asesor="${sessionScope.usuarioLogueado.getNombreCompleto()}"
                                                data-titulo="${tesis.titulo}">
                                                <i data-lucide="file-check" class="w-3 h-3 mr-1"></i> Resolución
                                            </button>
                                        </div>
                                    </td>
                                    
                                    <td class="px-6 py-4">
                                        <c:set var="evalKey" value="eval_${tesis.idTesis}" />
                                        <c:set var="evalPrev" value="${requestScope[evalKey]}" />
                                        
                                        <button class="btn-trigger-eval w-full text-sm font-bold px-3 py-1.5 rounded border transition ${tesis.estado == 'Aprobado' ? 'border-green-200 text-green-700 hover:bg-green-50' : 'bg-codex-sidebar text-codex-gold hover:bg-codex-darker border-transparent'}" 
                                            data-tesis-title="${tesis.titulo}" 
                                            data-tesis-id="${tesis.idTesis}" 
                                            data-archivo-path="${tesis.archivoPath}"
                                            <c:if test="${not empty evalPrev}">
                                                data-modo="recalificar"
                                                data-comentarios="${evalPrev.comentarios}"
                                                <c:forEach var="i" begin="1" end="38">data-item-${i}="${evalPrev['item'.concat(i)]}" </c:forEach>
                                            </c:if>
                                        >
                                            ${not empty evalPrev ? 'Modificar Nota' : 'Evaluar'}
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <!-- 3. MIS ESTUDIANTES -->
            <div id="mis-estudiantes" class="tab-content fade-in">
                <h2 class="text-xl font-bold text-codex-text mb-6 font-tech uppercase">Directorio de Estudiantes</h2>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                    <c:forEach var="tesis" items="${listaTesis}">
                        <div class="bg-white/95 backdrop-filter backdrop-blur-sm p-6 rounded-2xl shadow-sm border border-vino-200 hover:shadow-lg hover:border-codex-gold transition group flex flex-col">
                            <div class="flex items-center mb-4">
                                <div class="w-10 h-10 bg-vino-50 text-codex-sidebar border border-vino-100 rounded-full flex items-center justify-center font-bold mr-3 group-hover:bg-codex-sidebar group-hover:text-codex-gold transition">${fn:substring(tesis.nombreEstudiante,0,1)}</div>
                                <div><h4 class="font-bold text-slate-900 text-sm">${tesis.nombreEstudiante}</h4><p class="text-xs text-vino-400 font-mono">${tesis.codigoEstudiante}</p></div>
                            </div>
                            <p class="text-xs text-slate-500 mb-4 h-8 line-clamp-2 italic">"${tesis.titulo}"</p>
                            <div class="mt-auto border-t border-vino-50 pt-4 flex justify-between items-center">
                                <span class="text-xs font-bold px-2 py-1 rounded ${tesis.estado == 'Aprobado' ? 'bg-green-50 text-green-700' : 'bg-blue-50 text-blue-700'}">${tesis.estado}</span>
                                <c:set var="evalKeyEst" value="eval_${tesis.idTesis}" />
<c:set var="evalEst" value="${requestScope[evalKeyEst]}" />

<div class="space-x-2">
    <button class="btn-trigger-historial text-xs bg-gray-100 hover:bg-gray-200 text-gray-700 px-2 py-1 rounded transition font-semibold" data-student-name="${tesis.nombreEstudiante}">Historial</button>
    
    <button class="btn-trigger-detail text-xs text-codex-sidebar font-bold hover:text-codex-gold transition" 
            data-nombre-estudiante="${tesis.nombreEstudiante}" 
            data-codigo-estudiante="${tesis.codigoEstudiante}" 
            data-tesis-titulo="${tesis.titulo}" 
            data-tesis-estado="${tesis.estado}" 
            data-nota-asesor="${not empty evalEst ? evalEst.puntajeTotal : 'Pendiente'}">
            Detalles &rarr;
    </button>
</div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <!-- 5. JURADO (SUSTENTACIONES) -->
            <div id="jurado" class="tab-content fade-in">
                <h2 class="text-xl font-bold text-codex-text mb-6 font-tech uppercase">Sustentaciones Programadas</h2>
                <div class="bg-white/95 backdrop-filter backdrop-blur-sm rounded-2xl shadow-sm border border-vino-200 p-6">
                    <table class="min-w-full divide-y divide-vino-100">
                        <thead class="bg-vino-50"><tr><th>Expediente</th><th>Rol</th><th>Fecha</th><th>Lugar</th><th>Documentos</th><th>Acción</th></tr></thead>
                        <tbody class="divide-y divide-vino-50">
                            <c:forEach var="sus" items="${listaSustentaciones}">
                                <tr>
                                    <td class="px-6 py-4 text-sm font-bold text-slate-900">#${sus.idTramite}<br><span class="text-xs font-normal text-slate-500">${sus.nombreEstudiante}</span></td>
                                    <td class="px-6 py-4 text-sm text-slate-600"><span class="font-bold text-codex-sidebar">${sus.getRolJurado(sessionScope.usuarioLogueado.codigo)}</span></td>
                                    
                                    <td class="px-6 py-4 text-sm text-slate-600">
                                        <c:choose>
                                            <c:when test="${not empty sus.lugarEnlace && sus.lugarEnlace != 'Por definir'}">
                                                <fmt:formatDate value="${sus.fechaHora}" pattern="dd/MM/yyyy HH:mm"/>
                                            </c:when>
                                            <c:otherwise><span class="text-xs bg-yellow-100 text-yellow-700 px-2 py-1 rounded font-bold">Por programar</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    
                                    <td class="px-6 py-4 text-sm text-slate-600">
                                        ${not empty sus.lugarEnlace && sus.lugarEnlace != 'Por definir' ? sus.lugarEnlace : '-'}
                                    </td>
                                    
                                    <td class="px-6 py-4 text-sm flex flex-col gap-2">
                                        <c:if test="${not empty sus.archivoPath}">
                                            <a href="${pageContext.request.contextPath}/DownloadServlet?file=${sus.archivoPath}" 
                                               class="text-xs text-codex-sidebar hover:underline flex items-center font-bold bg-vino-50 px-2 py-1 rounded w-fit" 
                                               target="_blank">
                                                <i data-lucide="file-text" class="w-3 h-3 mr-1"></i> Ver Tesis (PDF)
                                            </a>
                                        </c:if>
                                        <c:if test="${empty sus.archivoPath}">
                                            <span class="text-xs text-slate-400 italic">Archivo no disponible</span>
                                        </c:if>

                                        <button class="text-xs text-purple-600 hover:underline flex items-center font-bold btn-ver-res-jurado w-fit"
                                                data-estudiante="${sus.nombreEstudiante}"
                                                data-m1="${sus.nombreMiembro1}" data-m2="${sus.nombreMiembro2}" data-m3="${sus.nombreMiembro3}" data-sup="${sus.nombreSuplente}"
                                                data-fecha="${not empty sus.lugarEnlace && sus.lugarEnlace != 'Por definir' ? fn:substring(sus.fechaHora, 0, 16).replace('T', ' ') : 'Por definir'}"
                                                data-lugar="${not empty sus.lugarEnlace ? sus.lugarEnlace : 'Por definir'}">
                                            <i data-lucide="file-check" class="w-3 h-3 mr-1"></i> Resolución
                                        </button>
                                    </td>
                                    
                                    <td class="px-6 py-4 flex flex-col space-y-2">
                                        <c:set var="evalJuryKey" value="evalJury_${sus.idSustentacion}" />
                                        <c:set var="evalJury" value="${requestScope[evalJuryKey]}" />
                                        
                                        <c:if test="${sus.estadoEvaluacionJurado == 'Evaluado'}">
                                            <button class="btn-jurado-detalle bg-vino-50 hover:bg-vino-100 text-codex-sidebar px-3 py-1 rounded text-xs font-bold transition border border-vino-200"
                                                data-estudiante="${sus.nombreEstudiante}"
                                                data-fecha="${not empty sus.lugarEnlace ? sus.fechaHora : 'Por definir'}"
                                                data-lugar="${sus.lugarEnlace}"
                                                data-nota="${not empty evalJury ? evalJury.puntajeTotal : 'Pendiente'}"
                                                data-observacion="${not empty evalJury ? evalJury.observaciones : ''}"
                                                <c:if test="${not empty evalJury}">
                                                     <c:forEach var="i" begin="1" end="38">data-item-${i}="${evalJury['item'.concat(i)]}" </c:forEach>
                                                </c:if>
                                                >Ver Detalle</button>
                                            
                                            <span class="text-[10px] text-green-600 font-bold border border-green-200 bg-green-50 px-2 py-1 rounded text-center flex justify-center items-center gap-1">
                                                <i data-lucide="check" class="w-3 h-3"></i> Votado (${evalJury.puntajeTotal})
                                            </span>
                                        </c:if>
                                        
                                        <c:if test="${sus.codigoSuplente != sessionScope.usuarioLogueado.codigo}">
                                            <c:if test="${sus.estadoEvaluacionJurado == 'Pendiente'}">
                                                <button class="btn-trigger-jurado-eval bg-purple-600 hover:bg-purple-700 text-white px-3 py-1 rounded text-xs font-bold transition shadow-sm" data-sustentacion-id="${sus.idSustentacion}" data-estudiante="${sus.nombreEstudiante}" data-titulo="${sus.tituloTesis}">Calificar</button>
                                            </c:if>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <!-- 6. REPORTES -->
            <div id="reportes" class="tab-content fade-in">
                <div class="bg-white/95 backdrop-filter backdrop-blur-sm rounded-2xl shadow-sm border border-vino-200 p-10 text-center">
                    <div class="mx-auto w-16 h-16 bg-vino-50 rounded-full flex items-center justify-center text-codex-sidebar mb-4">
                         <i data-lucide="file-bar-chart" class="w-8 h-8"></i>
                    </div>
                    <h3 class="text-lg font-bold text-codex-text mb-2 font-tech uppercase">Generar Reportes</h3>
                    <p class="text-slate-500 mb-6 max-w-md mx-auto">Descarga un archivo CSV consolidado con el estado, notas y observaciones de todas las tesis asignadas en este periodo.</p>
                    <form action="${pageContext.request.contextPath}/ReporteServlet" method="POST">
                        <input type="hidden" name="tipo_reporte" value="tesis_asignadas">
                        <button type="submit" class="bg-codex-sidebar hover:bg-codex-darker text-codex-gold px-8 py-3 rounded-xl font-bold shadow-lg transition transform hover:-translate-y-0.5 border-b-4 border-codex-darker active:border-b-0 active:translate-y-1">Descargar Reporte CSV</button>
                    </form>
                </div>
            </div>

        </main>
    </div>

    <!-- MODALES DE EVALUACIÓN Y DETALLE -->
    
    <!-- Modal Evaluación ASESOR -->
    <div id="evaluation-modal" class="modal-overlay"><div class="modal-content modal-content-xl bg-slate-50 overflow-hidden"><form id="evaluation-form" action="${pageContext.request.contextPath}/EvaluarServlet" method="POST" class="flex flex-col h-full"><input type="hidden" name="id_tesis" id="modal-hidden-tesis-id"><div class="bg-gradient-to-r from-codex-sidebar to-rose-700 p-6 border-b border-codex-darker flex justify-between items-center sticky top-0 z-10 shadow-md"><div class="text-white"><h3 class="text-xl font-bold font-tech uppercase">Evaluación de Tesis (Asesor)</h3><p class="text-sm text-vino-100 mt-1 opacity-90">Proyecto: <span id="modal-tesis-title" class="font-semibold text-codex-gold"></span></p></div><div class="flex items-center space-x-4"><a href="#" id="modal-tesis-download-link" target="_blank" class="text-sm bg-white/10 hover:bg-white/20 text-codex-gold border border-codex-gold/50 px-3 py-1.5 rounded-lg font-medium flex items-center transition"><i data-lucide="file-text" class="w-4 h-4 mr-2"></i> Ver PDF</a><button type="button" class="btn-close-modal p-2 bg-white/10 hover:bg-white/20 rounded-full text-white transition" data-modal-id="evaluation-modal"><i data-lucide="x" class="w-6 h-6"></i></button></div></div><div class="modal-body bg-slate-50 flex-1 overflow-y-auto custom-scroll p-6"><div class="bg-white rounded-xl shadow-sm border border-vino-100 overflow-hidden mb-6"><table class="rubrica-table w-full"><thead class="bg-vino-50"><tr><th class="w-12 text-center">#</th><th>Indicador</th><th class="w-24 text-center">1.0</th><th class="w-24 text-center">0.5</th><th class="w-24 text-center">0.0</th></tr></thead><tbody class="divide-y divide-vino-50"><c:set var="titulos" value="${fn:split('I. Título,II. Resumen,III. Introducción,IV. Problema,V. Justificación,VI. Objetivos,VII. Ética,VIII. Marco Teórico,IX. Hipótesis,X. Variables,XI. Metodología,XII. Resultados,XIII. Análisis,XIV. Conclusiones,XV. Recomendaciones,XVI. Referencias,XVII. Anexos,XVIII. Forma', ',')}" /><c:set var="indices" value="${fn:split('1,2,4,5,7,10,12,13,17,18,20,29,31,33,34,35,36,37', ',')}" /><c:forEach var="i" begin="1" end="38"><c:forEach var="idx" items="${indices}" varStatus="status"><c:if test="${i == idx}"><tr><td colspan="5" class="rubrica-section-row"><div class="rubrica-section">${titulos[status.index]}</div></td></tr></c:if></c:forEach><tr class="rubrica-row"><td class="text-center font-mono text-vino-300 font-bold">${i}</td><td>Ítem de evaluación #${i}</td><td class="text-center"><label class="score-label"><input type="radio" name="item_${i}" value="1.0" class="radio-score" onchange="calcularPuntaje('evaluation-form')"></label></td><td class="text-center"><label class="score-label"><input type="radio" name="item_${i}" value="0.5" class="radio-score" onchange="calcularPuntaje('evaluation-form')"></label></td><td class="text-center"><label class="score-label"><input type="radio" name="item_${i}" value="0.0" class="radio-score" checked onchange="calcularPuntaje('evaluation-form')"></label></td></tr></c:forEach></tbody></table></div><div><label class="block text-sm font-bold text-codex-text mb-2 uppercase">Comentarios Finales</label><textarea name="comentarios_revision" rows="4" class="w-full border border-vino-200 rounded-xl p-4 text-sm focus:ring-2 focus:ring-codex-sidebar outline-none bg-white" required></textarea></div></div><div class="modal-footer flex justify-between items-center gap-4 bg-white border-t border-vino-100 p-6"><div class="text-left"><span class="text-2xl font-tech font-bold text-slate-800">Puntaje: <span class="puntaje-total-display text-codex-sidebar">0.0</span> / 38</span><p class="condicion-calculada text-sm font-bold text-red-500 mt-1">Desaprobado</p></div><div class="flex space-x-3"><button type="button" class="btn-cancel-modal bg-white border border-slate-200 text-slate-600 px-6 py-3 rounded-xl font-bold" data-modal-id="evaluation-modal">Cancelar</button><button type="submit" class="bg-codex-sidebar hover:bg-codex-darker text-codex-gold px-8 py-3 rounded-xl font-bold shadow-lg border-b-4 border-codex-darker active:border-b-0 active:translate-y-1">Guardar Evaluación</button></div></div></form></div></div>

    <!-- Modal Evaluación JURADO (Escritura) -->
    <div id="jury-evaluation-modal" class="modal-overlay"><div class="modal-content modal-content-xl bg-slate-50 overflow-hidden"><form id="jury-evaluation-form" action="${pageContext.request.contextPath}/EvaluarJuradoServlet" method="POST" class="flex flex-col h-full"><input type="hidden" name="id_sustentacion" id="modal-jury-sustentacion-id"><div class="bg-gradient-jury p-6 border-b border-purple-600 flex justify-between items-center sticky top-0 z-10 shadow-md"><div class="text-white"><h3 class="text-xl font-bold font-tech uppercase">Evaluación de Defensa (Jurado)</h3><p class="text-sm text-purple-100 mt-1 opacity-90">Sustentante: <span id="modal-jury-estudiante" class="font-semibold text-white"></span></p></div><button type="button" class="btn-close-modal p-2 bg-white/10 hover:bg-white/20 rounded-full text-white transition" data-modal-id="jury-evaluation-modal"><i data-lucide="x" class="w-6 h-6"></i></button></div><div class="modal-body bg-slate-50 flex-1 overflow-y-auto custom-scroll p-6"><div class="bg-white rounded-xl shadow-sm border border-purple-100 overflow-hidden mb-6"><table class="rubrica-table w-full"><thead class="bg-purple-50"><tr><th class="w-12 text-center text-purple-900">#</th><th class="text-purple-900">Indicador (Defensa Oral y Tesis)</th><th class="w-24 text-center text-purple-900">1.0</th><th class="w-24 text-center text-purple-900">0.5</th><th class="w-24 text-center text-purple-900">0.0</th></tr></thead><tbody class="divide-y divide-purple-50"><c:forEach var="i" begin="1" end="38"><c:forEach var="idx" items="${indices}" varStatus="status"><c:if test="${i == idx}"><tr><td colspan="5" class="rubrica-section-row" style="background-color:#f3e8ff; color:#6b21a8; border-top:1px solid #d8b4fe;"><div class="rubrica-section">${titulos[status.index]}</div></td></tr></c:if></c:forEach><tr class="rubrica-row"><td class="text-center font-mono text-purple-300 font-bold">${i}</td><td>Ítem de evaluación #${i}</td><td class="text-center"><label class="score-label"><input type="radio" name="item_${i}" value="1.0" class="radio-score accent-purple-600" onchange="calcularPuntaje('jury-evaluation-form')"></label></td><td class="text-center"><label class="score-label"><input type="radio" name="item_${i}" value="0.5" class="radio-score accent-purple-600" onchange="calcularPuntaje('jury-evaluation-form')"></label></td><td class="text-center"><label class="score-label"><input type="radio" name="item_${i}" value="0.0" class="radio-score accent-purple-600" checked onchange="calcularPuntaje('jury-evaluation-form')"></label></td></tr></c:forEach></tbody></table></div><div><label class="block text-sm font-bold text-purple-900 mb-2">Dictamen / Observaciones</label><textarea name="comentarios_revision" rows="4" class="w-full border border-purple-200 rounded-xl p-4 text-sm focus:ring-2 focus:ring-purple-500 outline-none bg-white" required></textarea></div></div><div class="modal-footer flex justify-between items-center gap-4 bg-white border-t border-purple-100 p-6"><div class="text-left"><span class="text-2xl font-bold text-slate-800">Nota: <span class="puntaje-total-display text-purple-600">0.0</span> / 38</span><p class="condicion-calculada text-sm font-bold text-red-500 mt-1">Desaprobado</p></div><div class="flex space-x-3"><button type="button" class="btn-cancel-modal bg-white border border-purple-200 text-slate-600 px-6 py-3 rounded-xl font-bold" data-modal-id="jury-evaluation-modal">Cancelar</button><button type="submit" class="bg-purple-600 hover:bg-purple-700 text-white px-8 py-3 rounded-xl font-bold shadow-lg">Emitir Voto</button></div></div></form></div></div>

    <!-- Modal LECTURA DETALLADA JURADO -->
    <div id="modal-jurado-detalle" class="modal-overlay">
        <div class="modal-content modal-content-xl bg-slate-50 overflow-hidden">
            <div class="bg-gradient-jury p-6 border-b border-purple-600 flex justify-between items-center sticky top-0 z-10 shadow-md">
                <div class="text-white"><h3 class="text-xl font-bold font-tech uppercase">Detalle de Votación</h3><p class="text-sm text-purple-100 mt-1 opacity-90">Revisión de: <span id="jurado-estudiante" class="font-semibold"></span></p></div>
                <button type="button" class="btn-close-modal p-2 bg-white/10 hover:bg-white/20 rounded-full text-white transition" data-modal-id="modal-jurado-detalle"><i data-lucide="x" class="w-6 h-6"></i></button>
            </div>
            <div class="modal-body bg-slate-50 flex-1 overflow-y-auto custom-scroll p-6">
                <div class="bg-white p-4 rounded-xl mb-4 shadow-sm border border-purple-100 flex justify-between items-center">
                    <div><p class="text-xs text-purple-500 font-bold uppercase">Fecha Sustentación</p><p class="font-medium text-gray-800" id="jurado-fecha"></p></div>
                    <div><p class="text-xs text-purple-500 font-bold uppercase">Lugar</p><p class="font-medium text-gray-800" id="jurado-lugar"></p></div>
                    <div class="text-right"><p class="text-xs text-purple-500 font-bold uppercase">Nota Otorgada</p><p class="text-2xl font-bold text-purple-700" id="jurado-nota"></p></div>
                </div>
                <div class="bg-white rounded-xl shadow-sm border border-purple-100 overflow-hidden mb-6">
                    <table class="rubrica-table w-full">
                        <thead class="bg-purple-50"><tr><th class="w-12 text-center text-purple-900">#</th><th class="text-purple-900">Indicador</th><th class="w-24 text-center text-purple-900">Puntaje</th></tr></thead>
                        <tbody class="divide-y divide-purple-50"><c:forEach var="i" begin="1" end="38"><tr class="rubrica-row"><td class="text-center font-mono text-purple-300 font-bold">${i}</td><td>Ítem de evaluación #${i}</td><td class="text-center font-bold text-purple-700" id="read-item-${i}">-</td></tr></c:forEach></tbody>
                    </table>
                </div>
                <div><label class="block text-sm font-bold text-purple-900 mb-2">Observaciones Registradas</label><div class="w-full border border-purple-200 rounded-xl p-4 text-sm bg-white italic text-gray-600" id="jurado-observacion"></div></div>
            </div>
        </div>
    </div>

    <!-- Modal Detalle Estudiante -->
    <div id="modal-detalle-estudiante" class="modal-overlay"><div class="modal-content"><div class="bg-vino-50 p-6 flex justify-between items-center"><h3 class="text-xl font-bold text-codex-text font-tech uppercase">Detalle del Estudiante</h3><button type="button" class="btn-close-modal text-vino-300 hover:text-codex-text transition" data-modal-id="modal-detalle-estudiante"><i data-lucide="x" class="w-6 h-6"></i></button></div><div class="p-8 space-y-4"><div class="bg-white p-4 rounded-xl grid grid-cols-2 gap-4 border border-vino-100"><div><p class="text-xs font-bold text-vino-400 uppercase">Nombre</p><p class="font-semibold text-slate-900" id="detalle-nombre-estudiante"></p></div><div><p class="text-xs font-bold text-vino-400 uppercase">Código</p><p class="font-semibold text-slate-900" id="detalle-codigo-estudiante"></p></div></div><div><p class="text-xs font-bold text-vino-400 uppercase mb-1">Proyecto</p><p class="font-medium text-slate-800 leading-snug" id="detalle-tesis-titulo"></p></div><div class="grid grid-cols-2 gap-4"><div><p class="text-xs font-bold text-vino-400 uppercase mb-1">Estado</p><p class="font-bold text-codex-sidebar" id="detalle-tesis-estado"></p></div><div><p class="text-xs font-bold text-vino-400 uppercase mb-1">Nota Asesor</p><p class="font-bold text-slate-800 text-lg" id="detalle-nota-asesor"></p></div></div></div><div class="p-6 border-t border-vino-100 text-right bg-slate-50"><button type="button" class="btn-cancel-modal bg-white border border-vino-200 text-slate-600 px-6 py-2.5 rounded-xl font-bold shadow-sm" data-modal-id="modal-detalle-estudiante">Cerrar</button></div></div></div>
    
    <!-- Modal Historial Estudiante -->
    <div id="modal-historial-estudiante" class="modal-overlay">
        <div class="modal-content modal-content-xl bg-white overflow-hidden flex flex-col max-h-[80vh]">
            <div class="bg-vino-50 p-6 border-b border-vino-100 flex justify-between items-center">
                <h3 class="text-xl font-bold text-codex-text font-tech uppercase">Historial de Evaluaciones</h3>
                <button class="btn-close-modal text-vino-400 hover:text-codex-text" data-modal-id="modal-historial-estudiante"><i data-lucide="x" class="w-6 h-6"></i></button>
            </div>
            <div class="p-0 flex-1 overflow-y-auto">
                <table class="w-full text-left border-collapse">
                    <thead class="bg-vino-50 sticky top-0"><tr><th class="p-4 border-b text-xs font-bold text-vino-800 uppercase">Fecha</th><th class="p-4 border-b text-xs font-bold text-vino-800 uppercase">Resultado</th><th class="p-4 border-b text-xs font-bold text-vino-800 uppercase">Comentarios</th></tr></thead>
                    <tbody id="historial-table-body" class="text-sm text-slate-600"></tbody>
                </table>
                <div id="empty-history-msg" class="hidden p-8 text-center text-slate-400 italic">No hay evaluaciones registradas para este estudiante.</div>
            </div>
        </div>
    </div>
    
    <!-- ================== MODALES DE RESOLUCIÓN ================== -->

    <!-- A. RESOLUCIÓN DE ASESOR -->
    <div id="modal-res-asesor" class="modal-overlay">
        <div class="modal-content modal-document">
            <button onclick="closeModal('modal-res-asesor')" class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 no-print"><i data-lucide="x" class="w-6 h-6"></i></button>
            <div class="p-12 text-justify text-gray-900">
                <div class="doc-header">
                    <img src="https://upla.edu.pe/wp-content/uploads/2020/05/Logo-UPLA.png" alt="UPLA" class="h-16 mx-auto mb-4 opacity-80">
                    <h2 class="text-xl font-bold uppercase tracking-widest">Universidad Peruana Los Andes</h2>
                    <p class="text-sm text-gray-500 font-serif italic">Facultad de Ingeniería</p>
                </div>
                <div class="doc-title">RESOLUCIÓN DE DECANATO N° <span id="ra-num">234</span>-2025-DFI-UPLA</div>
                <div class="doc-body space-y-6">
                    <p><strong>VISTO:</strong> El expediente presentado para la aprobación e inscripción del Plan de Tesis del estudiante.</p>
                    <p><strong>CONSIDERANDO:</strong> Que el Reglamento General de Grados y Títulos establece la designación de un asesor para guiar la investigación.</p>
                    <p class="mt-4"><strong>SE RESUELVE:</strong></p>
                    <p><strong>ARTÍCULO PRIMERO:</strong> DESIGNAR al docente <strong id="ra-asesor" class="uppercase"></strong> como ASESOR del Plan de Tesis titulado: "<span id="ra-titulo" class="italic"></span>", presentado por el bachiller <strong id="ra-estudiante" class="uppercase"></strong>.</p>
                    <p><strong>ARTÍCULO SEGUNDO:</strong> Encargar a la Coordinación de Grados el cumplimiento de la presente resolución.</p>
                    <p class="text-center mt-8 font-bold">REGÍSTRESE, COMUNÍQUESE Y ARCHÍVESE.</p>
                </div>
                <div class="mt-20 text-center">
                    <div class="border-t border-black w-64 mx-auto pt-2 font-bold">Decano de la Facultad</div>
                </div>
            </div>
            <div class="p-6 bg-gray-50 rounded-b-xl flex justify-end no-print">
                <button onclick="window.print()" class="bg-slate-800 hover:bg-slate-900 text-white px-4 py-2 rounded font-bold flex items-center gap-2"><i data-lucide="printer" class="w-4 h-4"></i> Imprimir</button>
            </div>
        </div>
    </div>

    <!-- B. RESOLUCIÓN DE JURADO -->
    <div id="modal-res-jurado" class="modal-overlay">
        <div class="modal-content modal-document">
            <button onclick="closeModal('modal-res-jurado')" class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 no-print"><i data-lucide="x" class="w-6 h-6"></i></button>
            <div class="p-12 text-justify text-gray-900">
                <div class="doc-header">
                    <img src="https://upla.edu.pe/wp-content/uploads/2020/05/Logo-UPLA.png" alt="UPLA" class="h-16 mx-auto mb-4 opacity-80">
                    <h2 class="text-xl font-bold uppercase tracking-widest">Universidad Peruana Los Andes</h2>
                    <p class="text-sm text-gray-500 font-serif italic">Facultad de Ingeniería</p>
                </div>
                <div class="doc-title">RESOLUCIÓN DE DECANATO N° <span id="rj-num">892</span>-2025-DFI-UPLA</div>
                <div class="doc-body space-y-6">
                    <p><strong>VISTO:</strong> El expediente del bachiller <strong id="rj-estudiante" class="uppercase"></strong>, declarado APTO para la sustentación.</p>
                    <p class="mt-4"><strong>SE RESUELVE:</strong></p>
                    <p><strong>ARTÍCULO ÚNICO:</strong> CONFORMAR el Jurado Calificador para la sustentación de la tesis, integrado por:</p>
                    <ul class="list-disc list-inside ml-8">
                        <li><strong>Presidente:</strong> <span id="rj-m1"></span></li>
                        <li><strong>Secretario:</strong> <span id="rj-m2"></span></li>
                        <li><strong>Vocal:</strong> <span id="rj-m3"></span></li>
                        <li><strong>Suplente:</strong> <span id="rj-sup"></span></li>
                    </ul>
                    <p class="mt-4">La sustentación se llevará a cabo el <strong id="rj-fecha"></strong> en <strong id="rj-lugar"></strong>.</p>
                    <p class="text-center mt-8 font-bold">REGÍSTRESE, COMUNÍQUESE Y ARCHÍVESE.</p>
                </div>
                <div class="mt-20 text-center">
                    <div class="border-t border-black w-64 mx-auto pt-2 font-bold">Decano de la Facultad</div>
                </div>
            </div>
            <div class="p-6 bg-gray-50 rounded-b-xl flex justify-end no-print">
                <button onclick="window.print()" class="bg-slate-800 hover:bg-slate-900 text-white px-4 py-2 rounded font-bold flex items-center gap-2"><i data-lucide="printer" class="w-4 h-4"></i> Imprimir</button>
            </div>
        </div>
    </div>

    <!-- Datos de Historial -->
    <div id="history-data" class="hidden">
        <c:forEach var="h" items="${historialEvaluaciones}">
            <div class="history-item" data-student="${h.nombreEstudiante}" data-date="<fmt:formatDate value='${h.fechaEvaluacion}' pattern='dd/MM/yyyy'/>" data-cond="${h.condicion}" data-comment="${h.comentarios}"></div>
        </c:forEach>
    </div>

    <!-- SCRIPT CENTRALIZADO -->
    <script>
        lucide.createIcons();
        function showTab(tabId) {
            document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active', 'fade-in'));
            document.querySelectorAll('.tab-button').forEach(b => { b.classList.remove('tab-active'); b.classList.add('tab-inactive'); });
            document.getElementById(tabId).classList.add('active', 'fade-in');
            const btn = document.getElementById('btn-' + tabId);
            if(btn) { btn.classList.add('tab-active'); btn.classList.remove('tab-inactive'); }
            const titles = {'dashboard':'Dashboard', 'tesis-asignadas':'Tesis Asignadas', 'mis-estudiantes':'Mis Estudiantes', 'jurado':'Sustentaciones', 'reportes':'Reportes'};
            document.getElementById('header-title').textContent = titles[tabId] || 'Panel del Docente';
        }
        function openModal(id) { document.getElementById(id).classList.add('active'); }
        function closeModal(id) { document.getElementById(id).classList.remove('active'); }
        function logout() { window.location.href = '${pageContext.request.contextPath}/LogoutServlet'; }

        function calcularPuntaje(formId) {
            let total = 0;
            const form = document.getElementById(formId);
            form.querySelectorAll('input[type="radio"]:checked').forEach(r => total += parseFloat(r.value));
            form.querySelector('.puntaje-total-display').textContent = total.toFixed(1);
            const cond = form.querySelector('.condicion-calculada');
            if(total >= 25) { cond.textContent = "APROBADO"; cond.className = "condicion-calculada text-sm font-bold text-green-600 mt-1"; }
            else if(total >= 13) { cond.textContent = "OBSERVADO"; cond.className = "condicion-calculada text-sm font-bold text-yellow-600 mt-1"; }
            else { cond.textContent = "DESAPROBADO"; cond.className = "condicion-calculada text-sm font-bold text-red-600 mt-1"; }
        }

        function resetRubricaForm(formId) {
            const f = document.getElementById(formId);
            f.reset();
            f.querySelectorAll('input[value="0.0"]').forEach(r => r.checked = true);
            if(f.querySelector('textarea')) f.querySelector('textarea').value = "";
            calcularPuntaje(formId);
        }

        function cargarDatosPrevios(formId, btn) {
            const form = document.getElementById(formId);
            if(btn.dataset.comentarios) form.querySelector('textarea').value = btn.dataset.comentarios;
            for (let i = 1; i <= 38; i++) {
                let val = btn.getAttribute('data-item-' + i);
                if (val) { let radio = form.querySelector(`input[name="item_`+i+`"][value="`+val+`"]`); if (radio) radio.checked = true; }
            }
            calcularPuntaje(formId);
        }
        
        const notifBtn = document.getElementById('btn-notificaciones-toggle');
        const notifDropdown = document.getElementById('dropdown-notificaciones');
        if (notifBtn && notifDropdown) {
            notifBtn.addEventListener('click', (e) => { e.stopPropagation(); notifDropdown.classList.toggle('hidden'); });
            document.addEventListener('click', (e) => { if (!notifBtn.contains(e.target) && !notifDropdown.contains(e.target)) notifDropdown.classList.add('hidden'); });
        }

        document.addEventListener('DOMContentLoaded', () => {
            ['dashboard', 'tesis-asignadas', 'mis-estudiantes', 'reportes', 'jurado'].forEach(id => {
                document.getElementById('btn-' + id)?.addEventListener('click', () => showTab(id));
            });
            document.getElementById('btn-logout-header')?.addEventListener('click', logout);
            document.getElementById('btn-logout-sidebar')?.addEventListener('click', logout);
            const urlParams = new URLSearchParams(window.location.search);
            const tab = urlParams.get('tab');
            if (tab) showTab(tab); else showTab('dashboard');
            
            document.body.addEventListener('click', function(e) {
                if(e.target.closest('.btn-trigger-eval')) {
                    const btn = e.target.closest('.btn-trigger-eval');
                    document.getElementById('modal-tesis-title').textContent = btn.dataset.tesisTitle;
                    document.getElementById('modal-hidden-tesis-id').value = btn.dataset.tesisId;
                    const link = document.getElementById('modal-tesis-download-link');
                    const path = btn.dataset.archivoPath;
                    if(path && path !== 'null' && path !== '') { link.href = '${pageContext.request.contextPath}/DownloadServlet?file=' + path; link.style.display = 'flex'; } 
                    else { link.style.display = 'none'; }
                    if (btn.dataset.modo === 'recalificar') cargarDatosPrevios('evaluation-form', btn); else resetRubricaForm('evaluation-form');
                    openModal('evaluation-modal');
                }
                
                if(e.target.closest('.btn-ver-res-asesor')) {
                    const d = e.target.closest('.btn-ver-res-asesor').dataset;
                    document.getElementById('ra-estudiante').textContent = d.estudiante;
                    document.getElementById('ra-asesor').textContent = d.asesor;
                    document.getElementById('ra-titulo').textContent = d.titulo;
                    openModal('modal-res-asesor');
                }

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
                
                if(e.target.closest('.btn-trigger-jurado-eval')) {
                    const btn = e.target.closest('.btn-trigger-jurado-eval');
                    const d = btn.dataset;
                    document.getElementById('modal-jury-sustentacion-id').value = d.sustentacionId;
                    document.getElementById('modal-jury-estudiante').textContent = d.estudiante;
                    resetRubricaForm('jury-evaluation-form');
                    openModal('jury-evaluation-modal');
                }
                
                if(e.target.closest('.btn-trigger-detail')) {
                    const d = e.target.closest('.btn-trigger-detail').dataset;
                    document.getElementById('detalle-nombre-estudiante').textContent = d.nombreEstudiante;
                    document.getElementById('detalle-codigo-estudiante').textContent = d.codigoEstudiante;
                    document.getElementById('detalle-tesis-titulo').textContent = d.tesisTitulo;
                    document.getElementById('detalle-tesis-estado').textContent = d.tesisEstado;
                    const nota = d.notaAsesor && d.notaAsesor !== 'Pendiente' ? parseFloat(d.notaAsesor).toFixed(1) + ' / 38' : 'Pendiente';
                    document.getElementById('detalle-nota-asesor').textContent = nota;
                    openModal('modal-detalle-estudiante');
                }
                
                if(e.target.closest('.btn-jurado-detalle')) {
                    const btn = e.target.closest('.btn-jurado-detalle');
                    const d = btn.dataset;
                    document.getElementById('jurado-estudiante').textContent = d.estudiante;
                    document.getElementById('jurado-fecha').textContent = d.fecha;
                    document.getElementById('jurado-lugar').textContent = d.lugar;
                    document.getElementById('jurado-nota').textContent = d.nota !== 'Pendiente' ? d.nota + ' / 38' : 'Sin calificar';
                    document.getElementById('jurado-observacion').textContent = d.observacion ? '"' + d.observacion + '"' : 'Sin observaciones';
                    for (let i = 1; i <= 38; i++) {
                        let rawVal = btn.getAttribute('data-item-'+i);
                        document.getElementById('read-item-'+i).textContent = rawVal ? rawVal : '-';
                    }
                    openModal('modal-jurado-detalle');
                }

                if(e.target.closest('.btn-trigger-historial')) {
                    const targetStudent = e.target.closest('.btn-trigger-historial').dataset.studentName;
                    const tbody = document.getElementById('historial-table-body');
                    tbody.innerHTML = '';
                    let found = false;
                    document.querySelectorAll('#history-data .history-item').forEach(item => {
                        if(item.dataset.student === targetStudent) {
                            found = true;
                            const tr = document.createElement('tr');
                            tr.className = 'border-b border-slate-100 hover:bg-vino-50';
                            tr.innerHTML = '<td class="p-4">'+item.dataset.date+'</td><td class="p-4 font-bold '+ (item.dataset.cond === 'Aprobado' ? 'text-green-600' : 'text-red-600') +'">'+item.dataset.cond+'</td><td class="p-4 text-xs text-slate-500 italic">'+item.dataset.comment+'</td>';
                            tbody.appendChild(tr);
                        }
                    });
                    if(!found) document.getElementById('empty-history-msg').classList.remove('hidden');
                    else document.getElementById('empty-history-msg').classList.add('hidden');
                    openModal('modal-historial-estudiante');
                }
                
                if(e.target.closest('.btn-close-modal') || e.target.closest('.btn-cancel-modal')) {
                    const mId = e.target.closest('[data-modal-id]').getAttribute('data-modal-id');
                    closeModal(mId);
                }
                if(e.target.classList.contains('modal-overlay')) closeModal(e.target.id);
            });
        });
    </script>
</body>
</html>