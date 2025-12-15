<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Vista de Resolución</title>
    <script src="[https://cdn.tailwindcss.com](https://cdn.tailwindcss.com)"></script>
    <style>
        @media print {
            .no-print { display: none; }
            body { background: white; }
            .page { box-shadow: none; margin: 0; }
        }
        .page {
            width: 21cm; min-height: 29.7cm;
            padding: 2.5cm; margin: 1cm auto;
            background: white; box-shadow: 0 0 10px rgba(0,0,0,0.1);
            position: relative;
            font-family: "Times New Roman", serif;
            font-size: 11pt; line-height: 1.3;
        }
    </style>
</head>
<body class="bg-gray-100">

    <div class="fixed top-4 right-4 no-print space-x-2">
        <button onclick="window.print()" class="bg-blue-600 text-white px-4 py-2 rounded shadow hover:bg-blue-700">Imprimir / Guardar PDF</button>
        <button onclick="window.close()" class="bg-gray-500 text-white px-4 py-2 rounded shadow hover:bg-gray-600">Cerrar</button>
    </div>

    <div class="page">
        <!-- HEADER -->
        <div class="text-center mb-6">
            <h2 class="font-bold text-lg uppercase">Universidad Peruana Los Andes</h2>
            <p class="text-xs">Ley de Creación N° 23757</p>
            <p class="text-xs font-bold mt-2">FACULTAD DE INGENIERÍA - SECRETARÍA DOCENTE</p>
            <h1 class="font-bold text-xl mt-4 underline">RESOLUCIÓN DE DECANATO N° 2921-2025-DFI-UPLA</h1>
            <p class="text-right mt-4 italic">Huancayo, 17 de Noviembre del 2025</p>
        </div>

        <!-- BODY -->
        <div class="text-justify space-y-4">
            <p><strong>VISTO:</strong></p>
            <p>El expediente presentado por el bachiller, solicitando la designación de jurados de tesis.</p>

            <p><strong>CONSIDERANDO:</strong></p>
            <p>Que, la Constitución Política del Estado en su artículo 18, dispone que la Universidad es autónoma...</p>
            <p>Que, el Reglamento General de Grados y Títulos, articulo 34, precisa que "El Decano designa los jurados de tesis mediante acto resolutivo".</p>

            <p class="mt-4"><strong>RESUELVE:</strong></p>
            
            <p><strong>Art. 1º DESIGNAR</strong> como Jurados de la Tesis para optar el Título Profesional de INGENIERO DE SISTEMAS Y COMPUTACIÓN, según se detalla:</p>

            <div class="ml-8 mt-4 space-y-2 font-bold">
                <% 
                   // Simulación rápida de datos recibidos por URL para vista previa
                   String m1 = request.getParameter("m1") != null ? request.getParameter("m1") : "MTRO. JORGE ALBERTO VEGA FLORES";
                   String m2 = request.getParameter("m2") != null ? request.getParameter("m2") : "MG. RAUL ENRIQUE FERNANDEZ BEJARANO";
                   String m3 = request.getParameter("m3") != null ? request.getParameter("m3") : "MTRO. WALTER DAVID ESTARES VENTOCILLA";
                   String sup = request.getParameter("sup") != null ? request.getParameter("sup") : "DR. JORGE VLADIMIR PACHAS HUAYTAN";
                %>
                <p>MIEMBRO 1: <%= m1 %></p>
                <p>MIEMBRO 2: <%= m2 %></p>
                <p>MIEMBRO 3: <%= m3 %></p>
                <p>MIEMBRO SUPLENTE: <%= sup %> (Suplente)</p>
            </div>

            <p class="mt-4"><strong>Art. 2º ENCARGAR</strong> a la Coordinación de Grados y Títulos notificar a los interesados.</p>
            <p><strong>Art. 3º DISPONER</strong> que los docentes emitan su dictamen en un plazo no mayor de 15 días hábiles.</p>
            
            <p class="mt-8 text-center font-bold">REGÍSTRESE, COMUNÍQUESE Y ARCHÍVESE.</p>
        </div>

        <!-- FIRMA -->
        <div class="mt-20 text-center">
            <div class="w-64 border-t border-black mx-auto pt-2">
                <p class="font-bold">DR. CARLOS ROSARIO SANCHEZ GUZMAN</p>
                <p class="text-xs">Decano de la Facultad de Ingeniería</p>
            </div>
        </div>
    </div>

</body>
</html>