<%-- 
    Document    : admin.jsp
    DESCRIPCIÓN: Panel Administrativo Maestro - CODEX Blue/Gold Theme
    FUNCIONALIDAD: Completa (CRUD + Resoluciones + Puntajes + Notificaciones + Jurado)
    ESTILO: Blue/Gold Tech Premium con Glassmorphism
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
    <title>CODEX THESIS - Panel Administrador</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&family=Orbitron:wght@500;700;900&family=Merriweather:wght@400;700&display=swap" rel="stylesheet">
    <script src="https://unpkg.com/lucide@latest"></script>
    
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        codex: {
                            dark: '#0f172a',
                            darker: '#020617',
                            gold: '#facc15',
                            golddim: '#ca8a04',
                            bg: '#f1f5f9'
                        }
                    },
                    fontFamily: {
                        sans: ['Inter', 'sans-serif'],
                        tech: ['Orbitron', 'sans-serif'],
                    }
                }
            }
        }
    </script>
    
    <style>
        body { font-family: 'Inter', sans-serif; }
        
        /* Animaciones */
        .fade-in { animation: fadeIn 0.5s cubic-bezier(0.4, 0, 0.2, 1); }
        @keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }
        
        /* Patrón de fondo tech */
        .tech-bg-pattern {
            background-color: #f1f5f9;
            background-image: radial-gradient(rgba(15, 23, 42, 0.1) 1px, transparent 1px);
            background-size: 24px 24px;
        }

        /* Tarjetas Glassmorphism */
        .codex-card {
            background-color: rgba(255, 255, 255, 0.9);
            backdrop-filter: blur(12px);
            border: 1px solid #cbd5e1;
            box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.05);
            border-radius: 1rem;
        }

        /* Sidebar Navigation */
        .nav-item {
            transition: all 0.3s ease;
            border-left: 3px solid transparent;
            color: #94a3b8;
            border-radius: 0;
        }
        .nav-item:hover {
            background-color: rgba(255, 255, 255, 0.05);
            color: #facc15;
            padding-left: 1.75rem;
        }
        .tab-active {
            background-color: rgba(250, 204, 21, 0.1);
            color: #facc15 !important;
            border-left-color: #facc15;
            font-family: 'Orbitron', sans-serif;
            letter-spacing: 0.05em;
            text-shadow: 0 0 10px rgba(250, 204, 21, 0.2);
        }
        
        .tab-content { display: none; }
        .tab-content.active { display: block; }

        /* Modales */
        .modal-overlay {
            position: fixed; top: 0; left: 0; right: 0; bottom: 0;
            background-color: rgba(15, 23, 42, 0.85);
            backdrop-filter: blur(5px);
            display: none; align-items: center; justify-content: center;
            z-index: 50; animation: fadeIn 0.2s ease;
        }
        .modal-content {
            background-color: white;
            border-radius: 0.75rem;
            box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
            width: 90%; max-width: 550px;
            max-height: 90vh; overflow-y: auto;
            border-top: 4px solid #facc15;
        }
        .modal-overlay.active { display: flex; }
        
        /* Modal Documentos */
        .modal-document { 
            max-width: 800px; 
            background: white; 
            padding: 0; 
            font-family: 'Merriweather', serif; 
            color: #000; 
        }
        .doc-header { 
            text-align: center; 
            border-bottom: 1px solid #ccc; 
            padding-bottom: 1rem; 
            margin-bottom: 2rem; 
        }
        .doc-body { text-align: justify; line-height: 1.6; }
        .doc-title { 
            text-align: center; 
            font-weight: bold; 
            text-decoration: underline; 
            margin: 1.5rem 0; 
            text-transform: uppercase; 
        }
        
        /* Scrollbar */
        .custom-scroll::-webkit-scrollbar { height: 6px; width: 6px; }
        .custom-scroll::-webkit-scrollbar-track { background: #0f172a; }
        .custom-scroll::-webkit-scrollbar-thumb { background: #334155; border-radius: 3px; }
        .custom-scroll::-webkit-scrollbar-thumb:hover { background: #facc15; }
        
        /* Print Styles */
        @media print {
            body * { visibility: hidden; }
            .modal-overlay.active, .modal-overlay.active * { visibility: visible; }
            .modal-content { 
                position: absolute; left: 0; top: 0; width: 100%; 
                margin: 0; padding: 2cm; box-shadow: none; border: none; 
                max-width: 100%; max-height: none; overflow: visible; 
            }
            .no-print { display: none !important; }
            .modal-overlay { background: white; position: static; display: block; }
        }
    </style>
</head>
<body class="h-full flex flex-col lg:flex-row overflow-hidden">
    
    <!-- SIDEBAR -->
    <aside class="w-full lg:w-72 bg-codex-dark shadow-2xl flex-shrink-0 flex flex-col h-full border-r border-slate-800 z-30 no-print overflow-y-auto custom-scroll">
        <div class="flex flex-col h-full">
            
            <div class="text-center py-12 px-6 border-b border-slate-800 bg-[#0b1120] shrink-0">
                <div class="relative group cursor-pointer inline-block">
                    <div class="absolute -inset-0.5 bg-gradient-to-r from-codex-gold to-yellow-700 rounded-full opacity-60 group-hover:opacity-100 transition duration-1000 blur"></div>
                    <div class="relative h-20 w-20 rounded-full bg-slate-900 flex items-center justify-center border-2 border-slate-700 mx-auto">
                        <span class="font-tech text-2xl text-codex-gold font-bold">
                            ${fn:substring(sessionScope.usuarioLogueado.nombres, 0, 1)}${fn:substring(sessionScope.usuarioLogueado.apellidos, 0, 1)}
                        </span>
                    </div>
                </div>
                
                <h3 class="font-tech font-bold text-lg text-white mt-5 tracking-wide">${sessionScope.usuarioLogueado.getNombreCompleto()}</h3>
                <p class="text-codex-gold text-xs uppercase tracking-[0.2em] mb-4 opacity-80">Administrador</p>
                
                <span class="inline-flex items-center px-4 py-1.5 rounded-full border border-green-900 bg-green-900/20 text-xs font-bold text-green-400 tracking-wider">
                    <span class="flex h-2 w-2 relative mr-2">
                      <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-green-400 opacity-75"></span>
                      <span class="relative inline-flex rounded-full h-2 w-2 bg-green-500"></span>
                    </span>
                    SYSTEM ONLINE
                </span>
            </div>

            <div class="flex-1 overflow-y-auto custom-scroll p-6">
                <nav class="space-y-4">
                    <div onclick="showTab('dashboard')" id="btn-dashboard" class="nav-item tab-active w-full text-left px-6 py-4 text-base font-medium flex items-center gap-4 cursor-pointer">
                        <i data-lucide="layout-dashboard" class="w-6 h-6"></i> INICIO
                    </div>
                    
                    <p class="px-6 mt-10 mb-4 text-xs font-bold text-slate-500 uppercase tracking-[0.2em]">Gestión</p>
                    
                    <div onclick="showTab('usuarios')" id="btn-usuarios" class="nav-item w-full text-left px-6 py-4 text-base font-medium flex items-center gap-4 cursor-pointer">
                        <i data-lucide="users" class="w-6 h-6"></i> Usuarios
                    </div>
                    <div onclick="showTab('tesis-crud')" id="btn-tesis-crud" class="nav-item w-full text-left px-6 py-4 text-base font-medium flex items-center gap-4 cursor-pointer">
                        <i data-lucide="book-open" class="w-6 h-6"></i> Gestión de Tesis
                    </div>
                    
                    <p class="px-6 mt-10 mb-4 text-xs font-bold text-slate-500 uppercase tracking-[0.2em]">Titulación</p>
                    
                    <div onclick="showTab('tramites-gestion')" id="btn-tramites-gestion" class="nav-item w-full text-left px-6 py-4 text-base font-medium flex items-center gap-4 cursor-pointer">
                        <i data-lucide="file-check" class="w-6 h-6"></i> Trámites
                    </div>
                </nav>
            </div>
            
            <div class="mt-auto p-6 border-t border-slate-800 lg:hidden shrink-0">
                <button onclick="logout()" class="w-full bg-red-900/30 text-red-400 border border-red-900/50 px-6 py-3 rounded-lg text-base font-medium">Cerrar Sesión</button>
            </div>
        </div>
    </aside>

    <!-- MAIN CONTENT -->
    <div class="flex-1 flex flex-col h-screen overflow-hidden tech-bg-pattern relative">
        
        <!-- HEADER -->
        <header class="bg-white/80 backdrop-blur-md h-20 flex items-center justify-between px-10 shrink-0 z-20 no-print border-b border-slate-200">
            <div>
                <h1 class="text-2xl font-tech font-bold text-slate-800 uppercase tracking-tight">CODEX <span class="text-codex-gold">PANEL</span></h1>
                <p class="text-xs text-slate-400 font-mono mt-1">Admin Access v4.2 // Secure Connection</p>
            </div>
            <div class="flex items-center space-x-4">
                <!-- NOTIFICACIONES -->
                <div class="relative">
                    <button id="btn-notificaciones-toggle" class="p-2 hover:bg-slate-100 rounded-full transition text-slate-500 hover:text-codex-gold relative focus:outline-none">
                        <i data-lucide="bell" class="w-5 h-5"></i>
                        <c:if test="${not empty notificaciones}">
                            <span class="absolute top-1 right-1 h-2 w-2 bg-red-500 rounded-full animate-pulse"></span>
                        </c:if>
                    </button>
                    <div id="dropdown-notificaciones" class="hidden absolute right-0 mt-2 w-80 bg-white border border-slate-200 rounded-xl shadow-xl z-50 overflow-hidden">
                        <div class="bg-slate-50 px-4 py-3 border-b border-slate-100">
                            <p class="text-xs font-bold text-slate-800 uppercase">Notificaciones</p>
                        </div>
                        <div class="max-h-64 overflow-y-auto custom-scroll">
                            <c:forEach var="noti" items="${notificaciones}">
                                <div class="p-3 hover:bg-slate-50 border-b border-slate-50 text-sm">
                                    <p class="text-slate-800 font-medium">${noti.mensaje}</p>
                                    <p class="text-xs text-slate-400 mt-1"><fmt:formatDate value="${noti.fechaEnvio}" pattern="dd MMM, HH:mm"/></p>
                                </div>
                            </c:forEach>
                            <c:if test="${empty notificaciones}">
                                <div class="p-6 text-center text-sm text-slate-400">Sin novedades.</div>
                            </c:if>
                        </div>
                    </div>
                </div>
                
                <div class="h-6 w-px bg-slate-200"></div>
                
                <button id="btn-logout-header" class="group relative inline-flex items-center justify-center px-6 py-2.5 overflow-hidden font-bold text-white transition-all duration-300 bg-red-600 rounded-lg hover:bg-red-700 shadow-md shadow-red-500/20 border-b-4 border-red-800 active:border-b-0 active:translate-y-1">
                    <span class="mr-2">Cerrar Sesión</span>
                    <i data-lucide="log-out" class="w-4 h-4"></i>
                </button>
            </div>
        </header>

        <!-- CONTENT AREA -->
        <main class="flex-1 overflow-auto p-8 custom-scroll">
            
            <!-- Mensajes -->
            <c:if test="${not empty adminMsg}">
                <div class="p-4 mb-6 bg-emerald-50 border-l-4 border-emerald-500 text-emerald-800 rounded shadow-sm fade-in flex items-center codex-card">
                    <i data-lucide="check-circle" class="w-5 h-5 mr-3"></i>
                    <span class="font-medium">${adminMsg}</span>
                </div>
            </c:if>
            <c:if test="${not empty adminError}">
                <div class="p-4 mb-6 bg-red-50 border-l-4 border-red-500 text-red-800 rounded shadow-sm fade-in flex items-center codex-card">
                    <i data-lucide="alert-circle" class="w-5 h-5 mr-3"></i>
                    <span class="font-medium">${adminError}</span>
                </div>
            </c:if>
            
            <!-- 1. DASHBOARD -->
            <div id="dashboard" class="tab-content fade-in">
                <h2 class="text-xl font-tech font-bold text-slate-700 mb-6 uppercase border-l-4 border-codex-gold pl-3 tracking-wider">Vista General</h2>
                
                <!-- Contadores -->
                <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
                    <div class="bg-codex-dark rounded-xl p-6 text-white shadow-xl relative overflow-hidden group border border-slate-700">
                        <div class="absolute right-0 top-0 h-20 w-20 bg-white/5 rounded-bl-full transition transform group-hover:scale-110"></div>
                        <p class="text-xs font-bold text-slate-400 uppercase tracking-widest">Usuarios</p>
                        <div class="flex items-end justify-between mt-2">
                            <p class="text-4xl font-tech font-bold text-codex-gold">${totalEstudiantes + totalDocentes}</p>
                            <i data-lucide="users" class="w-8 h-8 text-slate-600 group-hover:text-codex-gold transition"></i>
                        </div>
                    </div>
                    <div class="bg-codex-dark rounded-xl p-6 text-white shadow-xl relative overflow-hidden group border border-slate-700">
                        <div class="absolute right-0 top-0 h-20 w-20 bg-white/5 rounded-bl-full transition transform group-hover:scale-110"></div>
                        <p class="text-xs font-bold text-slate-400 uppercase tracking-widest">Tesis Activas</p>
                        <div class="flex items-end justify-between mt-2">
                            <p class="text-4xl font-tech font-bold text-yellow-500">${tesisEnProceso}</p>
                            <i data-lucide="file-text" class="w-8 h-8 text-slate-600 group-hover:text-yellow-500 transition"></i>
                        </div>
                    </div>
                    <div class="bg-codex-dark rounded-xl p-6 text-white shadow-xl relative overflow-hidden group border border-slate-700">
                        <div class="absolute right-0 top-0 h-20 w-20 bg-white/5 rounded-bl-full transition transform group-hover:scale-110"></div>
                        <p class="text-xs font-bold text-slate-400 uppercase tracking-widest">Titulados</p>
                        <div class="flex items-end justify-between mt-2">
                            <p class="text-4xl font-tech font-bold text-emerald-400">${tesisCompletadas}</p>
                            <i data-lucide="award" class="w-8 h-8 text-slate-600 group-hover:text-emerald-400 transition"></i>
                        </div>
                    </div>
                    <div class="bg-codex-dark rounded-xl p-6 text-white shadow-xl relative overflow-hidden group border border-slate-700">
                        <div class="absolute right-0 top-0 h-20 w-20 bg-white/5 rounded-bl-full transition transform group-hover:scale-110"></div>
                        <p class="text-xs font-bold text-slate-400 uppercase tracking-widest">Docentes</p>
                        <div class="flex items-end justify-between mt-2">
                            <p class="text-4xl font-tech font-bold text-blue-400">${totalDocentes}</p>
                            <i data-lucide="briefcase" class="w-8 h-8 text-slate-600 group-hover:text-blue-400 transition"></i>
                        </div>
                    </div>
                </div>
                
                <!-- Acciones Rápidas -->
                <div class="codex-card p-8">
    <h3 class="text-lg font-tech font-bold text-slate-800 mb-6 flex items-center">
        <i data-lucide="zap" class="w-5 h-5 mr-2 text-codex-gold"></i> Accesos Directos
    </h3>
    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <button onclick="showTab('usuarios')" class="p-6 border border-codex-gold/60 bg-slate-50/50 rounded-xl hover:bg-slate-100 hover:border-codex-gold transition text-left group shadow-md shadow-codex-gold/10">
            <i data-lucide="user-plus" class="w-6 h-6 text-blue-500 mb-2 group-hover:scale-110 transition-transform"></i>
            <span class="block font-bold text-slate-800">Gestionar Usuarios</span>
        </button>
        
        <button onclick="openModal('modal-nueva-tesis')" class="p-6 border border-codex-gold/60 bg-slate-50/50 rounded-xl hover:bg-slate-100 hover:border-codex-gold transition text-left group shadow-md shadow-codex-gold/10">
            <i data-lucide="file-plus" class="w-6 h-6 text-yellow-500 mb-2 group-hover:scale-110 transition-transform"></i>
            <span class="block font-bold text-slate-800">Nueva Tesis</span>
        </button>
        
        <button onclick="showTab('tramites-gestion')" class="p-6 border border-codex-gold/60 bg-slate-50/50 rounded-xl hover:bg-slate-100 hover:border-codex-gold transition text-left group shadow-md shadow-codex-gold/10">
            <i data-lucide="gavel" class="w-6 h-6 text-green-500 mb-2 group-hover:scale-110 transition-transform"></i>
            <span class="block font-bold text-slate-800">Sustentaciones</span>
        </button>
    </div>
</div>
            </div>

            <!-- 2. GESTIÓN USUARIOS -->
            <div id="usuarios" class="tab-content fade-in">
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
                    
                    <div class="codex-card overflow-hidden border border-codex-gold/60 shadow-md shadow-codex-gold/10">
                        <div class="px-6 py-4 border-b border-slate-100 flex justify-between items-center bg-slate-50">
                            <h3 class="font-tech font-bold text-slate-700">Docentes</h3>
                            <div class="flex gap-2">
                                <input type="text" id="search-docente" onkeyup="filtrarTabla('search-docente', 'tabla-docentes')" placeholder="Buscar..." class="pl-2 pr-2 py-1 text-xs border rounded">
                                <button onclick="openModal('modal-docente')" class="text-xs bg-codex-dark text-codex-gold px-3 py-1.5 rounded hover:bg-slate-800 flex items-center gap-1 border border-codex-gold/30"><i data-lucide="plus" class="w-3 h-3"></i> Nuevo</button>
                            </div>
                        </div>
                        <div class="max-h-96 overflow-y-auto custom-scroll">
                            <table class="w-full text-sm text-left">
                                <thead class="bg-white text-slate-500 border-b"><tr><th class="px-4 py-2">Nombre</th><th class="px-4 py-2">Acción</th></tr></thead>
                                <tbody id="tabla-docentes">
                                    <c:forEach var="doc" items="${listaDocentes}">
                                        <tr class="border-b hover:bg-slate-50">
                                            <td class="px-4 py-3"><div class="font-medium text-slate-800">${doc.getNombreCompleto()}</div><div class="text-xs text-slate-400">${doc.email}</div></td>
                                            <td class="px-4 py-3 flex gap-2">
                                                <button class="text-indigo-600 hover:text-indigo-800 btn-abrir-modal-editar" data-codigo="${doc.codigo}" data-nombres="${doc.nombres}" data-apellidos="${doc.apellidos}" data-email="${doc.email}" data-rol="docente"><i data-lucide="edit-2" class="w-4 h-4"></i></button>
                                                <button class="text-red-600 hover:text-red-800 btn-abrir-modal-eliminar" data-codigo="${doc.codigo}" data-nombre="${doc.getNombreCompleto()}"><i data-lucide="trash-2" class="w-4 h-4"></i></button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="codex-card overflow-hidden border border-codex-gold/60 shadow-md shadow-codex-gold/10">
                        <div class="px-6 py-4 border-b border-slate-100 flex justify-between items-center bg-slate-50">
                            <h3 class="font-tech font-bold text-slate-700">Estudiantes</h3>
                            <div class="flex gap-2">
                                <input type="text" id="search-estudiante" onkeyup="filtrarTabla('search-estudiante', 'tabla-estudiantes')" placeholder="Buscar..." class="pl-2 pr-2 py-1 text-xs border rounded">
                                <button onclick="openModal('modal-estudiante')" class="text-xs bg-codex-dark text-codex-gold px-3 py-1.5 rounded hover:bg-slate-800 flex items-center gap-1 border border-codex-gold/30"><i data-lucide="plus" class="w-3 h-3"></i> Nuevo</button>
                            </div>
                        </div>
                        <div class="max-h-96 overflow-y-auto custom-scroll">
                            <table class="w-full text-sm text-left">
                                <thead class="bg-white text-slate-500 border-b"><tr><th class="px-4 py-2">Nombre</th><th class="px-4 py-2">Acción</th></tr></thead>
                                <tbody id="tabla-estudiantes">
                                    <c:forEach var="alu" items="${listaEstudiantes}">
                                        <tr class="border-b hover:bg-slate-50">
                                            <td class="px-4 py-3"><div class="font-medium text-slate-800">${alu.getNombreCompleto()}</div><div class="text-xs text-slate-400">${alu.codigo}</div></td>
                                            <td class="px-4 py-3 flex gap-2">
                                                <button class="text-indigo-600 hover:text-indigo-800 btn-abrir-modal-editar" data-codigo="${alu.codigo}" data-nombres="${alu.nombres}" data-apellidos="${alu.apellidos}" data-email="${alu.email}" data-rol="alumno"><i data-lucide="edit-2" class="w-4 h-4"></i></button>
                                                <button class="text-red-600 hover:text-red-800 btn-abrir-modal-eliminar" data-codigo="${alu.codigo}" data-nombre="${alu.getNombreCompleto()}"><i data-lucide="trash-2" class="w-4 h-4"></i></button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 3. GESTIÓN TESIS -->
            <div id="tesis-crud" class="tab-content fade-in">
                <div class="flex justify-between items-center mb-6">
                    <h2 class="text-xl font-tech font-bold text-slate-800 uppercase">Gestión de Tesis</h2>
                    <div class="flex gap-2">
                        <input type="text" id="search-tesis" onkeyup="filtrarTabla('search-tesis', 'tabla-tesis')" placeholder="Buscar tesis..." class="pl-3 pr-3 py-2 text-sm border rounded-xl">
                        <button onclick="openModal('modal-nueva-tesis')" class="bg-codex-dark text-codex-gold px-5 py-2.5 rounded-xl font-bold text-sm shadow-md hover:bg-slate-800 flex items-center gap-2 border border-codex-gold/30"><i data-lucide="plus-circle" class="w-4 h-4"></i> Nueva Tesis</button>
                    </div>
                </div>
                
                <div class="codex-card overflow-hidden border border-codex-gold/60 shadow-md shadow-codex-gold/10">
                    <table class="w-full text-left text-sm">
                        <thead class="bg-slate-50 text-slate-500 font-bold border-b">
                            <tr><th class="px-6 py-4">Título</th><th class="px-6 py-4">Estudiante</th><th class="px-6 py-4">Asesor</th><th class="px-6 py-4">Estado</th><th class="px-6 py-4">Acciones</th></tr>
                        </thead>
                        <tbody id="tabla-tesis" class="divide-y divide-slate-100">
                            <c:forEach var="tesis" items="${listaTotalTesis}">
                                <tr class="hover:bg-slate-50 transition">
                                    <td class="px-6 py-4 font-medium text-slate-900 max-w-xs truncate" title="${tesis.titulo}">${tesis.titulo}</td>
                                    <td class="px-6 py-4 text-slate-600">${tesis.nombreEstudiante}</td>
                                    <td class="px-6 py-4 text-slate-600">${empty tesis.nombreDocenteRevisor ? '<span class="italic text-slate-400">-</span>' : tesis.nombreDocenteRevisor}</td>
                                    <td class="px-6 py-4"><span class="px-2 py-1 text-xs font-bold rounded-full border ${tesis.estado == 'Aprobado' ? 'bg-emerald-100 text-emerald-700 border-emerald-200' : 'bg-yellow-100 text-yellow-700 border-yellow-200'}">${tesis.estado}</span></td>
                                    <td class="px-6 py-4 flex gap-2 items-center">
                                        <c:if test="${tesis.estado != 'Aprobado'}">
                                            <button class="btn-abrir-modal-reasignar text-indigo-600 bg-indigo-50 px-2 py-1 rounded hover:bg-indigo-100" data-tesis-id="${tesis.idTesis}" data-tesis-titulo="${tesis.titulo}" data-docente-actual="${tesis.codigoDocenteRevisor}" title="Asignar Asesor"><i data-lucide="user-check" class="w-4 h-4"></i></button>
                                            <button class="btn-abrir-modal-editar-tesis text-blue-600 bg-blue-50 px-2 py-1 rounded hover:bg-blue-100" data-tesis-id="${tesis.idTesis}" data-tesis-titulo="${tesis.titulo}" data-archivo-actual="${tesis.archivoPath}" title="Editar Tesis"><i data-lucide="edit-2" class="w-4 h-4"></i></button>
                                            <button class="btn-abrir-modal-eliminar-tesis text-red-600 bg-red-50 px-2 py-1 rounded hover:bg-red-100" data-tesis-id="${tesis.idTesis}" data-tesis-titulo="${tesis.titulo}" data-archivo-actual="${tesis.archivoPath}" title="Eliminar Tesis"><i data-lucide="trash-2" class="w-4 h-4"></i></button>
                                        </c:if>
                                        <c:if test="${not empty tesis.nombreDocenteRevisor}">
                                            <button class="text-purple-600 hover:text-purple-800 px-2 py-1 btn-ver-res-asesor bg-purple-50 rounded" data-estudiante="${tesis.nombreEstudiante}" data-asesor="${tesis.nombreDocenteRevisor}" data-titulo="${tesis.titulo}" title="Ver Resolución Asesor"><i data-lucide="file-text" class="w-4 h-4"></i></button>
                                        </c:if>
                                        <c:if test="${not empty tesis.archivoPath}">
                                            <a href="DownloadServlet?file=${tesis.archivoPath}" class="text-slate-500 hover:text-slate-800 px-2 py-1 bg-slate-100 rounded" title="Descargar PDF" target="_blank"><i data-lucide="download" class="w-4 h-4"></i></a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- 4. GESTIÓN DE TRÁMITES -->
            <div id="tramites-gestion" class="tab-content fade-in">
                <div class="flex justify-between items-center mb-6">
                    <h2 class="text-xl font-tech font-bold text-slate-800 uppercase">Trámites de Titulación</h2>
                    <div class="flex gap-2">
                        <input type="text" id="search-tramite" onkeyup="filtrarTabla('search-tramite', 'tabla-tramites')" placeholder="Buscar trámite..." class="pl-3 pr-3 py-2 text-sm border rounded-xl">
                    </div>
                </div>
                
                <div class="codex-card overflow-hidden p-6 border border-codex-gold/60 shadow-md shadow-codex-gold/10">
                    <table class="w-full text-left text-sm">
                        <thead class="bg-slate-50 text-slate-500 font-bold border-b"><tr><th class="px-6 py-4">Estudiante</th><th class="px-6 py-4">Estado Actual</th><th class="px-6 py-4">Gestión</th></tr></thead>
                        <tbody id="tabla-tramites" class="divide-y divide-slate-100">
                            <c:forEach var="t" items="${listaTramites}">
                                <tr class="hover:bg-slate-50 transition">
                                    <td class="px-6 py-4"><div class="font-bold text-slate-900">${t.nombreEstudiante}</div><div class="text-xs text-slate-400">${t.codigoEstudiante}</div></td>
                                    <td class="px-6 py-4"><span class="px-3 py-1 text-xs font-bold rounded-full ${t.estadoActual == 'Titulado' ? 'bg-emerald-100 text-emerald-700' : 'bg-blue-100 text-blue-700'}">${t.estadoActual}</span></td>
                                    <td class="px-6 py-4 flex gap-3">
                                        <c:choose>
                                            <c:when test="${t.estadoActual == 'Titulado'}">
                                                <span class="text-xs text-green-600 font-bold flex items-center gap-1 bg-green-50 px-3 py-1.5 rounded border border-green-100 cursor-default"><i data-lucide="check-circle-2" class="w-4 h-4"></i> Finalizado</span>
                                            </c:when>
                                            <c:otherwise>
                                                <button onclick="postAndRedirect('AdminTramitesServlet', {action: 'ver_detalle_tramite', id_tramite: '${t.idTramite}', tipo_vista: 'docs'})" class="flex items-center gap-2 px-3 py-1.5 bg-slate-100 text-slate-700 rounded-lg hover:bg-slate-200 text-xs font-bold transition border border-slate-200">
                                                    <i data-lucide="folder" class="w-4 h-4"></i> Expediente
                                                </button>
                                                <button onclick="postAndRedirect('AdminTramitesServlet', {action: 'ver_detalle_tramite', id_tramite: '${t.idTramite}', tipo_vista: 'jury'})" class="flex items-center gap-2 px-3 py-1.5 bg-purple-50 text-purple-700 rounded-lg hover:bg-purple-100 text-xs font-bold transition border border-purple-200">
                                                    <i data-lucide="gavel" class="w-4 h-4"></i> Jurado
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>

    <!-- MODAL DOCUMENTOS (Expediente) -->
    <c:if test="${openModalDocs && param_type == 'docs'}">
        <div id="modal-gestion-docs" class="modal-overlay active">
            <div class="modal-content">
                <div class="p-6 border-b flex justify-between items-center bg-slate-50 rounded-t-lg">
                    <h3 class="font-tech font-bold text-slate-800">Revisión de Expediente</h3>
                    <button type="button" onclick="closeModal('modal-gestion-docs')" class="text-slate-400 hover:text-slate-600"><i data-lucide="x"></i></button>
                </div>
                <div class="p-6 space-y-4 max-h-[70vh] overflow-y-auto custom-scroll">
                    <c:forEach var="doc" items="${listaDocumentosModal}">
                        <div class="flex justify-between items-center p-3 border rounded-lg bg-white shadow-sm">
                            <div><p class="font-bold text-sm text-slate-700">${doc.tipoDocumento}</p><a href="DownloadServlet?file=${doc.rutaArchivo}" target="_blank" class="text-xs text-blue-600 hover:underline">Ver Archivo</a></div>
                            <div class="flex items-center gap-2">
                                <c:if test="${doc.estadoValidacion == 'Pendiente'}">
                                    <form action="AdminTramitesServlet" method="POST"><input type="hidden" name="action" value="validar_documento"><input type="hidden" name="id_documento" value="${doc.idDocumento}"><input type="hidden" name="id_tramite" value="${doc.idTramite}"><input type="hidden" name="estado" value="Validado"><button class="p-1 bg-green-100 text-green-600 rounded hover:bg-green-200"><i data-lucide="check" class="w-4 h-4"></i></button></form>
                                    <button onclick="document.getElementById('rechazo-${doc.idDocumento}').classList.toggle('hidden')" class="p-1 bg-red-100 text-red-600 rounded hover:bg-red-200"><i data-lucide="x" class="w-4 h-4"></i></button>
                                </c:if>
                                <c:if test="${doc.estadoValidacion != 'Pendiente'}"><span class="text-xs font-bold px-2 py-1 rounded ${doc.estadoValidacion == 'Validado' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}">${doc.estadoValidacion}</span></c:if>
                            </div>
                        </div>
                        <div id="rechazo-${doc.idDocumento}" class="hidden p-2 bg-red-50 rounded border border-red-100"><form action="AdminTramitesServlet" method="POST"><input type="hidden" name="action" value="validar_documento"><input type="hidden" name="id_documento" value="${doc.idDocumento}"><input type="hidden" name="id_tramite" value="${doc.idTramite}"><input type="hidden" name="estado" value="Rechazado"><input type="text" name="observacion" class="w-full border text-xs p-1 mb-2" placeholder="Motivo..." required><button class="w-full bg-red-600 text-white text-xs py-1 rounded">Confirmar Rechazo</button></form></div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </c:if>

    <!-- MODAL JURADO INTELIGENTE -->
    <c:if test="${openModalDocs && param_type == 'jury'}">
        <div id="modal-gestion-jurado" class="modal-overlay active">
            <div class="modal-content">
                <div class="bg-gradient-to-r from-purple-600 to-purple-700 p-6 text-white flex justify-between items-center rounded-t-lg">
                    <h3 class="font-tech font-bold">Gestión de Jurado</h3>
                    <button type="button" onclick="closeModal('modal-gestion-jurado')" class="text-white opacity-70 hover:opacity-100"><i data-lucide="x"></i></button>
                </div>
                <div class="p-6 space-y-6">
                    <!-- FASE 2: DESIGNACIÓN -->
                    <div class="bg-slate-50 p-4 rounded-lg border border-slate-200">
                        <div class="flex justify-between items-center mb-3">
                            <h4 class="font-tech font-bold text-sm text-slate-700 uppercase">1. Designación de Jurado</h4>
                            <c:if test="${not empty sustentacionActual.codigoMiembro1}"><span class="text-xs bg-green-100 text-green-700 px-2 py-1 rounded font-bold">Designado</span></c:if>
                        </div>
                        <form action="AdminTramitesServlet" method="POST" class="space-y-3">
                            <input type="hidden" name="action" value="designar_jurado">
                            <input type="hidden" name="id_tramite" value="${modalTramiteId}">
                            <div class="grid grid-cols-2 gap-2">
                                <select name="miembro1" class="text-xs border rounded p-1.5 w-full" required><option value="">Presidente...</option><c:forEach var="d" items="${listaDocentesDropdown}"><option value="${d.codigo}" ${sustentacionActual.codigoMiembro1 == d.codigo ? 'selected' : ''}>${d.nombres}</option></c:forEach></select>
                                <select name="miembro2" class="text-xs border rounded p-1.5 w-full" required><option value="">Secretario...</option><c:forEach var="d" items="${listaDocentesDropdown}"><option value="${d.codigo}" ${sustentacionActual.codigoMiembro2 == d.codigo ? 'selected' : ''}>${d.nombres}</option></c:forEach></select>
                                <select name="miembro3" class="text-xs border rounded p-1.5 w-full" required><option value="">Vocal...</option><c:forEach var="d" items="${listaDocentesDropdown}"><option value="${d.codigo}" ${sustentacionActual.codigoMiembro3 == d.codigo ? 'selected' : ''}>${d.nombres}</option></c:forEach></select>
                                <select name="suplente" class="text-xs border rounded p-1.5 w-full border-yellow-300"><option value="">Suplente...</option><c:forEach var="d" items="${listaDocentesDropdown}"><option value="${d.codigo}" ${sustentacionActual.codigoSuplente == d.codigo ? 'selected' : ''}>${d.nombres}</option></c:forEach></select>
                            </div>
                            <div class="flex justify-end"><button type="submit" class="bg-purple-600 text-white text-xs font-bold px-4 py-2 rounded hover:bg-purple-700">Guardar Designación</button></div>
                        </form>
                    </div>

                    <!-- ESTADO DE EVALUACIÓN + RESOLUCIÓN -->
                    <c:if test="${not empty sustentacionActual.codigoMiembro1}">
                        <div class="p-4 border rounded-lg bg-white">
                            <div class="flex justify-between items-center mb-2">
                                <h4 class="font-tech font-bold text-sm text-slate-700 uppercase">2. Estado de Evaluación</h4>
                                <button type="button" class="text-purple-600 hover:text-purple-800 text-xs font-bold flex items-center btn-ver-res-jurado"
                                    data-estudiante="${sustentacionActual.nombreEstudiante}"
                                    data-m1="${sustentacionActual.nombreMiembro1}" 
                                    data-m2="${sustentacionActual.nombreMiembro2}" 
                                    data-m3="${sustentacionActual.nombreMiembro3}" 
                                    data-sup="${sustentacionActual.nombreSuplente}"
                                    data-fecha="${not empty sustentacionActual.lugarEnlace ? fn:substring(sustentacionActual.fechaHora, 0, 16).replace('T', ' ') : 'Por definir'}" 
                                    data-lugar="${not empty sustentacionActual.lugarEnlace ? sustentacionActual.lugarEnlace : 'Por definir'}">
                                    <i data-lucide="file-text" class="w-4 h-4 mr-1"></i> Ver Resolución
                                </button>
                            </div>
                            <div class="grid grid-cols-3 gap-2 text-center text-xs">
                                <div class="p-2 rounded ${sustentacionActual.tieneVotoM1 ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-500'}"><span class="block font-bold mb-1">Pres.</span>${sustentacionActual.tieneVotoM1 ? sustentacionActual.puntaje1 : 'Pendiente'}</div>
                                <div class="p-2 rounded ${sustentacionActual.tieneVotoM2 ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-500'}"><span class="block font-bold mb-1">Sec.</span>${sustentacionActual.tieneVotoM2 ? sustentacionActual.puntaje2 : 'Pendiente'}</div>
                                <div class="p-2 rounded ${sustentacionActual.tieneVotoM3 ? 'bg-green-100 text-green-700' : 'bg-gray-100 text-gray-500'}"><span class="block font-bold mb-1">Voc.</span>${sustentacionActual.tieneVotoM3 ? sustentacionActual.puntaje3 : 'Pendiente'}</div>
                            </div>
                        </div>
                    </c:if>

                    <!-- FASE 3: PROGRAMACIÓN -->
                    <div class="bg-slate-50 p-4 rounded-lg border border-slate-200 ${not empty sustentacionActual.codigoMiembro1 ? '' : 'opacity-50 pointer-events-none'}">
                        <h4 class="font-tech font-bold text-sm text-slate-700 mb-3 uppercase">3. Programación de Defensa</h4>
                        <c:if test="${!sustentacionActual.tieneVotoM1 || !sustentacionActual.tieneVotoM2 || !sustentacionActual.tieneVotoM3}"><p class="text-xs text-red-500 mb-2 italic">Recomendación: Esperar a que los 3 jurados emitan su voto favorable.</p></c:if>
                        <form action="AdminTramitesServlet" method="POST" class="space-y-3">
                            <input type="hidden" name="action" value="programar_sustentacion">
                            <input type="hidden" name="id_tramite" value="${modalTramiteId}">
                            <div class="flex gap-2">
                                <div class="w-1/2"><label class="text-[10px] font-bold text-slate-500 uppercase">Fecha y Hora</label><input type="datetime-local" name="fecha_hora" class="text-xs border rounded p-1.5 w-full" required value="${fn:replace(sustentacionActual.fechaHora, ' ', 'T')}"></div>
                                <div class="w-1/2"><label class="text-[10px] font-bold text-slate-500 uppercase">Lugar / Enlace</label><input type="text" name="lugar" class="text-xs border rounded p-1.5 w-full" placeholder="Auditorio o Zoom" required value="${sustentacionActual.lugarEnlace}"></div>
                            </div>
                            <div class="flex justify-end pt-2"><button type="submit" class="bg-indigo-600 text-white text-xs font-bold px-4 py-2 rounded hover:bg-indigo-700">Programar Sustentación</button></div>
                        </form>
                    </div>

                    <!-- FASE 4: CIERRE -->
                    <c:if test="${sustentacionActual.notaFinal >= 13}">
                        <div class="text-center pt-2 border-t border-slate-200 mt-2">
                            <p class="text-xs font-bold uppercase text-slate-500">Nota Promedio</p>
                            <p class="text-2xl font-tech font-bold text-slate-800 mb-2"><fmt:formatNumber value="${sustentacionActual.notaFinal}" maxFractionDigits="1"/></p>
                            <form action="AdminTramitesServlet" method="POST">
                                <input type="hidden" name="action" value="cerrar_sustentacion">
                                <input type="hidden" name="id_tramite" value="${modalTramiteId}">
                                <button class="w-full bg-green-600 hover:bg-green-700 text-white py-3 rounded-lg font-bold text-sm shadow-lg animate-pulse"><i data-lucide="award" class="w-4 h-4 inline mr-1"></i> FINALIZAR Y TITULAR</button>
                            </form>
                        </div>
                    </c:if>
                    
                    <!-- Control Estado Manual -->
                    <div class="bg-white p-3 rounded-lg shadow-sm border border-slate-200 mt-4">
                        <div class="flex items-center mb-2"><div class="w-5 h-5 rounded-full bg-yellow-100 text-yellow-600 flex items-center justify-center mr-2 font-bold text-[10px]">!</div><h4 class="font-bold text-xs text-slate-800">Control de Estado (Manual)</h4></div>
                        <form action="AdminTramitesServlet" method="POST" class="flex gap-2">
                            <input type="hidden" name="action" value="avanzar_fase"><input type="hidden" name="id_tramite" value="${modalTramiteId}">
                            <select name="nueva_fase" class="border-slate-300 rounded p-1.5 text-xs flex-1"><option value="Iniciado">Iniciado</option><option value="Revisión de Carpeta">Revisión de Carpeta</option><option value="Designación de Jurado">Designación de Jurado</option><option value="Apto para Sustentar">Apto para Sustentar</option><option value="Sustentación Programada">Sustentación Programada</option><option value="Titulado">Titulado</option></select>
                            <button type="submit" class="bg-slate-200 text-slate-700 font-bold px-3 py-1.5 rounded text-xs hover:bg-slate-300">Cambiar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <!-- MODALES DE RESOLUCIONES -->
    <div id="modal-res-asesor" class="modal-overlay"><div class="modal-content modal-document"><button onclick="closeModal('modal-res-asesor')" class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 no-print"><i data-lucide="x" class="w-6 h-6"></i></button><div class="p-12 text-justify text-gray-900"><div class="doc-header"><img src="https://upla.edu.pe/wp-content/uploads/2020/05/Logo-UPLA.png" alt="UPLA" class="h-16 mx-auto mb-4 opacity-80"><h2 class="text-xl font-bold uppercase tracking-widest">Universidad Peruana Los Andes</h2><p class="text-sm text-gray-500 font-serif italic">Facultad de Ingeniería</p></div><div class="doc-title">RESOLUCIÓN DE DECANATO N° <span id="ra-num">234</span>-2025-DFI-UPLA</div><div class="doc-body space-y-6"><p><strong>VISTO:</strong> El expediente presentado para la aprobación e inscripción del Plan de Tesis del estudiante.</p><p><strong>CONSIDERANDO:</strong> Que el Reglamento General de Grados y Títulos establece la designación de un asesor para guiar la investigación.</p><p class="mt-4"><strong>SE RESUELVE:</strong></p><p><strong>ARTÍCULO PRIMERO:</strong> DESIGNAR al docente <strong id="ra-asesor" class="uppercase"></strong> como ASESOR del Plan de Tesis titulado: "<span id="ra-titulo" class="italic"></span>", presentado por el bachiller <strong id="ra-estudiante" class="uppercase"></strong>.</p><p><strong>ARTÍCULO SEGUNDO:</strong> Encargar a la Coordinación de Grados el cumplimiento de la presente resolución.</p><p class="text-center mt-8 font-bold">REGÍSTRESE, COMUNÍQUESE Y ARCHÍVESE.</p></div><div class="mt-20 text-center"><div class="border-t border-black w-64 mx-auto pt-2 font-bold">Decano de la Facultad</div></div></div><div class="p-6 bg-gray-50 rounded-b-xl flex justify-end no-print"><button onclick="window.print()" class="bg-codex-dark hover:bg-slate-800 text-white px-4 py-2 rounded font-bold flex items-center gap-2"><i data-lucide="printer" class="w-4 h-4"></i> Imprimir</button></div></div></div>
    
    <div id="modal-res-jurado" class="modal-overlay"><div class="modal-content modal-document"><button onclick="closeModal('modal-res-jurado')" class="absolute top-4 right-4 text-gray-400 hover:text-gray-600 no-print"><i data-lucide="x" class="w-6 h-6"></i></button><div class="p-12 text-justify text-gray-900"><div class="doc-header"><img src="https://upla.edu.pe/wp-content/uploads/2020/05/Logo-UPLA.png" alt="UPLA" class="h-16 mx-auto mb-4 opacity-80"><h2 class="text-xl font-bold uppercase tracking-widest">Universidad Peruana Los Andes</h2><p class="text-sm text-gray-500 font-serif italic">Facultad de Ingeniería</p></div><div class="doc-title">RESOLUCIÓN DE DECANATO N° <span id="rj-num">892</span>-2025-DFI-UPLA</div><div class="doc-body space-y-6"><p><strong>VISTO:</strong> El expediente del bachiller <strong id="rj-estudiante" class="uppercase"></strong>, declarado APTO para la sustentación.</p><p><strong>CONSIDERANDO:</strong></p><p><strong>ARTÍCULO ÚNICO:</strong> CONFORMAR el Jurado Calificador para la sustentación de la tesis, integrado por:</p><ul class="list-disc list-inside ml-8"><li><strong>Presidente:</strong> <span id="rj-m1"></span></li><li><strong>Secretario:</strong> <span id="rj-m2"></span></li><li><strong>Vocal:</strong> <span id="rj-m3"></span></li><li><strong>Suplente:</strong> <span id="rj-sup"></span></li></ul><p class="mt-4">Los miembros del Jurado tiene 20 días hábiles para evaluar la Tesis o se reasignara un nuevo jurado.</p><p class="text-center mt-8 font-bold">REGÍSTRESE, COMUNÍQUESE Y ARCHÍVESE.</p></div><div class="mt-20 text-center"><div class="border-t border-black w-64 mx-auto pt-2 font-bold">Decano de la Facultad</div></div></div><div class="p-6 bg-gray-50 rounded-b-xl flex justify-end no-print"><button onclick="window.print()" class="bg-codex-dark hover:bg-slate-800 text-white px-4 py-2 rounded font-bold flex items-center gap-2"><i data-lucide="printer" class="w-4 h-4"></i> Imprimir</button></div></div></div>

    <!-- MODALES CRUD -->
    <div id="modal-docente" class="modal-overlay"><div class="modal-content"><form action="UsuarioAdminServlet" method="POST"><input type="hidden" name="action" value="crear"><input type="hidden" name="rol" value="docente"><div class="p-6 border-b bg-slate-50"><h3 class="font-tech font-bold text-lg">Nuevo Docente</h3></div><div class="p-6 space-y-4"><input name="codigo" class="w-full border p-2 rounded" placeholder="Código" required><input name="nombres" class="w-full border p-2 rounded" placeholder="Nombres" required><input name="apellidos" class="w-full border p-2 rounded" placeholder="Apellidos" required><input name="email" class="w-full border p-2 rounded" placeholder="Email" required><input type="password" name="password" class="w-full border p-2 rounded" placeholder="Contraseña" required></div><div class="p-6 flex justify-end gap-2 bg-slate-50"><button type="button" class="btn-close-modal bg-gray-200 px-4 py-2 rounded font-bold" data-modal-id="modal-docente">Cancelar</button><button class="bg-codex-dark text-codex-gold px-4 py-2 rounded font-bold border border-codex-gold/30">Guardar</button></div></form></div></div>
    
    <div id="modal-estudiante" class="modal-overlay"><div class="modal-content"><form action="UsuarioAdminServlet" method="POST"><input type="hidden" name="action" value="crear"><input type="hidden" name="rol" value="alumno"><div class="p-6 border-b bg-slate-50"><h3 class="font-tech font-bold text-lg">Nuevo Estudiante</h3></div><div class="p-6 space-y-4"><input name="codigo" class="w-full border p-2 rounded" placeholder="Código" required><input name="nombres" class="w-full border p-2 rounded" placeholder="Nombres" required><input name="apellidos" class="w-full border p-2 rounded" placeholder="Apellidos" required><input name="email" class="w-full border p-2 rounded" placeholder="Email" required><input type="password" name="password" class="w-full border p-2 rounded" placeholder="Contraseña" required></div><div class="p-6 flex justify-end gap-2 bg-slate-50"><button type="button" class="btn-close-modal bg-gray-200 px-4 py-2 rounded font-bold" data-modal-id="modal-estudiante">Cancelar</button><button class="bg-codex-dark text-codex-gold px-4 py-2 rounded font-bold border border-codex-gold/30">Guardar</button></div></form></div></div>
    
    <div id="modal-editar-usuario" class="modal-overlay"><div class="modal-content"><form action="UsuarioAdminServlet" method="POST"><input type="hidden" name="action" value="editar"><input type="hidden" id="editar_codigo" name="codigo"><input type="hidden" id="editar_rol" name="rol"><div class="p-6 border-b bg-slate-50"><h3 class="font-tech font-bold text-lg">Editar Usuario</h3></div><div class="p-6 space-y-4"><input id="editar_codigo_display" class="w-full bg-slate-100 p-2 rounded" readonly><input id="editar_nombres" name="nombres" class="w-full border p-2 rounded" required><input id="editar_apellidos" name="apellidos" class="w-full border p-2 rounded" required><input id="editar_email" name="email" class="w-full border p-2 rounded" required><input type="password" name="password" class="w-full border p-2 rounded" placeholder="Nueva Contraseña (Opcional)"></div><div class="p-6 flex justify-end gap-2 bg-slate-50"><button type="button" class="btn-close-modal bg-gray-200 px-4 py-2 rounded font-bold" data-modal-id="modal-editar-usuario">Cancelar</button><button class="bg-indigo-600 text-white px-4 py-2 rounded font-bold">Actualizar</button></div></form></div></div>
    
    <div id="modal-eliminar-usuario" class="modal-overlay"><div class="modal-content"><form action="UsuarioAdminServlet" method="POST"><input type="hidden" name="action" value="eliminar"><input type="hidden" id="eliminar_codigo" name="codigo"><div class="p-8 text-center"><h3 class="text-lg font-tech font-bold">¿Eliminar a <span id="eliminar_nombre" class="text-red-600"></span>?</h3><div class="mt-6 flex justify-center space-x-3"><button type="button" class="btn-cancel-modal bg-white border px-4 py-2 rounded font-bold" data-modal-id="modal-eliminar-usuario">Cancelar</button><button type="submit" class="bg-red-600 text-white px-4 py-2 rounded font-bold">Sí, Eliminar</button></div></div></form></div></div>
    
    <div id="modal-nueva-tesis" class="modal-overlay"><div class="modal-content"><form action="TesisAdminServlet" method="POST" enctype="multipart/form-data"><input type="hidden" name="action" value="crear"><div class="p-6 border-b flex justify-between items-center bg-slate-50"><h3 class="font-tech font-bold text-lg">Registrar Tesis</h3><button type="button" class="btn-close-modal text-slate-400" data-modal-id="modal-nueva-tesis"><i data-lucide="x"></i></button></div><div class="p-6 space-y-4"><div><label class="block text-xs font-bold uppercase text-slate-500 mb-1">Título</label><input type="text" name="tesis_titulo" class="w-full border rounded p-2 text-sm" required></div><div class="grid grid-cols-2 gap-4"><div><label class="block text-xs font-bold uppercase text-slate-500 mb-1">Estudiante</label><select name="alumno_id" class="w-full border rounded p-2 text-sm" required><option value="">Seleccionar...</option><c:forEach var="alu" items="${listaEstudiantes}"><option value="${alu.codigo}">${alu.getNombreCompleto()}</option></c:forEach></select></div><div><label class="block text-xs font-bold uppercase text-slate-500 mb-1">Asesor (Opcional)</label><select name="docente_id" class="w-full border rounded p-2 text-sm"><option value="">Sin Asignar</option><c:forEach var="doc" items="${listaDocentes}"><option value="${doc.codigo}">${doc.getNombreCompleto()}</option></c:forEach></select></div></div><div><label class="block text-xs font-bold uppercase text-slate-500 mb-1">Archivo PDF</label><input type="file" name="tesis_file" accept=".pdf" class="w-full border rounded p-1 text-sm" required></div></div><div class="p-6 flex justify-end bg-slate-50 rounded-b-lg"><button type="submit" class="bg-codex-dark text-codex-gold px-4 py-2 rounded text-sm font-bold border border-codex-gold/30">Guardar</button></div></form></div></div>
    
    <div id="modal-reasignar-tesis" class="modal-overlay"><div class="modal-content"><form action="TesisAdminServlet" method="POST"><input type="hidden" name="action" value="reasignar"><input type="hidden" name="id_tesis" id="reasignar_id_tesis"><div class="p-6 border-b bg-slate-50"><h3 class="font-tech font-bold">Asignar Asesor</h3></div><div class="p-6 space-y-4"><p id="reasignar_tesis_titulo" class="mb-2 text-sm font-bold"></p><select name="docente_id" id="reasignar_docente_id" class="w-full border p-2 rounded"><option value="">[Sin Asignar]</option><c:forEach var="d" items="${listaDocentes}"><option value="${d.codigo}">${d.getNombreCompleto()}</option></c:forEach></select></div><div class="p-6 flex justify-end gap-2 bg-slate-50"><button type="button" class="btn-close-modal bg-gray-200 px-3 py-1 rounded font-bold" data-modal-id="modal-reasignar-tesis">Cancelar</button><button class="bg-blue-600 text-white px-3 py-1 rounded font-bold">Guardar</button></div></form></div></div>
    
    <div id="modal-editar-tesis" class="modal-overlay"><div class="modal-content"><form action="TesisAdminServlet" method="POST" enctype="multipart/form-data"><input type="hidden" name="action" value="editar"><input type="hidden" name="id_tesis" id="editar_id_tesis"><input type="hidden" name="existing_file_path" id="editar_existing_file_path"><div class="p-6 border-b bg-slate-50"><h3 class="font-tech font-bold">Editar Tesis</h3></div><div class="p-6 space-y-4"><input id="editar_tesis_titulo" name="tesis_titulo" class="w-full border p-2 rounded" required><p class="text-xs bg-slate-50 p-1">Actual: <span id="editar_archivo_actual_nombre"></span></p><input type="file" name="tesis_file" accept=".pdf" class="w-full border p-2 rounded"></div><div class="p-6 flex justify-end gap-2 bg-slate-50"><button type="button" class="btn-close-modal bg-gray-200 px-3 py-1 rounded font-bold" data-modal-id="modal-editar-tesis">Cancelar</button><button class="bg-blue-600 text-white px-3 py-1 rounded font-bold">Actualizar</button></div></form></div></div>
    
    <div id="modal-eliminar-tesis" class="modal-overlay"><div class="modal-content"><form action="TesisAdminServlet" method="POST"><input type="hidden" name="action" value="eliminar"><input type="hidden" id="eliminar_id_tesis" name="id_tesis"><input type="hidden" id="eliminar_existing_file_path" name="existing_file_path"><div class="p-8 text-center"><h3 class="text-lg font-tech font-bold">¿Eliminar Tesis?</h3><p id="eliminar_tesis_titulo" class="text-sm my-2 text-slate-600"></p><div class="mt-6 flex justify-center space-x-3"><button type="button" class="btn-cancel-modal bg-white border px-4 py-2 rounded font-bold" data-modal-id="modal-eliminar-tesis">Cancelar</button><button type="submit" class="bg-red-600 text-white px-4 py-2 rounded font-bold">Eliminar</button></div></div></form></div></div>

    <!-- FORMULARIO OCULTO GLOBAL -->
    <form id="global-post-form" method="POST" style="display:none;"></form>

    <script>
        try { lucide.createIcons(); } catch(e) {}
        
        function postAndRedirect(url, params) {
            const form = document.getElementById('global-post-form');
            form.action = url;
            form.innerHTML = '';
            for (const key in params) {
                if (params.hasOwnProperty(key)) {
                    const hiddenField = document.createElement('input');
                    hiddenField.type = 'hidden';
                    hiddenField.name = key;
                    hiddenField.value = params[key];
                    form.appendChild(hiddenField);
                }
            }
            form.submit();
        }

        function filtrarTabla(inputId, tableId) {
            const input = document.getElementById(inputId);
            const filter = input.value.toLowerCase();
            const tbody = document.getElementById(tableId);
            const rows = tbody.getElementsByTagName("tr");
            for (let i = 0; i < rows.length; i++) {
                const cells = rows[i].getElementsByTagName("td");
                let match = false;
                for (let j = 0; j < cells.length; j++) {
                    if (cells[j] && cells[j].innerText.toLowerCase().indexOf(filter) > -1) { match = true; break; }
                }
                rows[i].style.display = match ? "" : "none";
            }
        }

        function showTab(tabId) {
            document.querySelectorAll('.tab-content').forEach(c => c.style.display = 'none');
            document.querySelectorAll('.nav-item').forEach(b => { b.classList.remove('tab-active'); });
            const content = document.getElementById(tabId);
            if(content) content.style.display = 'block';
            const btn = document.getElementById('btn-' + tabId);
            if (btn) { btn.classList.add('tab-active'); }
        }

        const activeTabServer = "${activeTab}";
        if (activeTabServer && activeTabServer !== '') { showTab(activeTabServer); } else { showTab('dashboard'); }

        function openModal(modalId) { const m = document.getElementById(modalId); if(m) m.classList.add('active'); }
        function closeModal(modalId) { const m = document.getElementById(modalId); if(m) m.classList.remove('active'); }
        function logout() { window.location.href = 'LogoutServlet'; }

        // LOGICA NOTIFICACIONES
        const notifBtn = document.getElementById('btn-notificaciones-toggle');
        const notifDropdown = document.getElementById('dropdown-notificaciones');
        if (notifBtn && notifDropdown) {
            notifBtn.addEventListener('click', (e) => { e.stopPropagation(); notifDropdown.classList.toggle('hidden'); });
            document.addEventListener('click', (e) => { if (!notifBtn.contains(e.target) && !notifDropdown.contains(e.target)) notifDropdown.classList.add('hidden'); });
        }

        document.addEventListener('DOMContentLoaded', () => {
            document.getElementById('btn-logout-header').addEventListener('click', logout);
            document.querySelectorAll('.btn-close-modal, .btn-cancel-modal').forEach(btn => btn.addEventListener('click', () => closeModal(btn.getAttribute('data-modal-id'))));
            document.querySelectorAll('.modal-overlay').forEach(o => o.addEventListener('click', (e) => { if(e.target === o) closeModal(o.id); }));
            
            // LISTENERS DINÁMICOS
            document.body.addEventListener('click', function(e) {
                
                // 1. Ver Resolución Jurado
                if (e.target.closest('.btn-ver-res-jurado')) {
                    const btn = e.target.closest('.btn-ver-res-jurado');
                    const d = btn.dataset;
                    document.getElementById('rj-estudiante').textContent = d.estudiante;
                    document.getElementById('rj-m1').textContent = d.m1;
                    document.getElementById('rj-m2').textContent = d.m2;
                    document.getElementById('rj-m3').textContent = d.m3;
                    document.getElementById('rj-sup').textContent = d.sup;
                    openModal('modal-res-jurado');
                }
                
                // 2. Ver Resolución Asesor
                if (e.target.closest('.btn-ver-res-asesor')) {
                    const btn = e.target.closest('.btn-ver-res-asesor');
                    const d = btn.dataset;
                    document.getElementById('ra-estudiante').textContent = d.estudiante;
                    document.getElementById('ra-asesor').textContent = d.asesor;
                    document.getElementById('ra-titulo').textContent = d.titulo;
                    openModal('modal-res-asesor');
                }

                // 3. CRUD Usuario - Editar
                if (e.target.closest('.btn-abrir-modal-editar')) {
                    const btn = e.target.closest('.btn-abrir-modal-editar');
                    const d = btn.dataset;
                    document.getElementById('editar_codigo').value = d.codigo; 
                    document.getElementById('editar_codigo_display').value = d.codigo; 
                    document.getElementById('editar_rol').value = d.rol; 
                    document.getElementById('editar_nombres').value = d.nombres; 
                    document.getElementById('editar_apellidos').value = d.apellidos; 
                    document.getElementById('editar_email').value = d.email; 
                    openModal('modal-editar-usuario');
                }

                // 4. CRUD Usuario - Eliminar
                if (e.target.closest('.btn-abrir-modal-eliminar')) {
                    const btn = e.target.closest('.btn-abrir-modal-eliminar');
                    document.getElementById('eliminar_codigo').value = btn.dataset.codigo; 
                    document.getElementById('eliminar_nombre').textContent = btn.dataset.nombre; 
                    openModal('modal-eliminar-usuario');
                }
                
                // 5. CRUD Tesis - Reasignar
                if (e.target.closest('.btn-abrir-modal-reasignar')) {
                    e.preventDefault();
                    const btn = e.target.closest('.btn-abrir-modal-reasignar');
                    document.getElementById('reasignar_id_tesis').value = btn.dataset.tesisId; 
                    document.getElementById('reasignar_tesis_titulo').textContent = btn.dataset.tesisTitulo; 
                    document.getElementById('reasignar_docente_id').value = btn.dataset.docenteActual || ""; 
                    openModal('modal-reasignar-tesis');
                }

                // 6. CRUD Tesis - Editar
                if (e.target.closest('.btn-abrir-modal-editar-tesis')) {
                    e.preventDefault(); 
                    const btn = e.target.closest('.btn-abrir-modal-editar-tesis');
                    document.getElementById('editar_id_tesis').value = btn.dataset.tesisId; 
                    document.getElementById('editar_tesis_titulo').value = btn.dataset.tesisTitulo; 
                    document.getElementById('editar_existing_file_path').value = btn.dataset.archivoActual; 
                    document.getElementById('editar_archivo_actual_nombre').textContent = btn.dataset.archivoActual ? btn.dataset.archivoActual.split('/').pop() : 'Ninguno'; 
                    openModal('modal-editar-tesis');
                }

                // 7. CRUD Tesis - Eliminar
                if (e.target.closest('.btn-abrir-modal-eliminar-tesis')) {
                    e.preventDefault(); 
                    const btn = e.target.closest('.btn-abrir-modal-eliminar-tesis');
                    document.getElementById('eliminar_id_tesis').value = btn.dataset.tesisId; 
                    document.getElementById('eliminar_tesis_titulo').textContent = btn.dataset.tesisTitulo; 
                    document.getElementById('eliminar_existing_file_path').value = btn.dataset.archivoActual; 
                    openModal('modal-eliminar-tesis');
                }
            });
        });
    </script>
</body>
</html>