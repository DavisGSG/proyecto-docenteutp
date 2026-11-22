-- ============ USUARIOS ============
-- Admin:       admin@utp.edu.pe     / admin1234
-- Estudiantes: alumno1..4@utp.edu.pe / estudiante123
INSERT IGNORE INTO usuarios (id, nombre, correo, password, carrera, ciclo, rol) VALUES
(1, 'Coordinación Académica', 'admin@utp.edu.pe',   '$2a$10$n/ROWDM17JXpydYh1mNeuualIcai5ShrOBQvhITi9f/4rkcbwFIp6', 'Administración', 1, 'ADMIN'),
(2, 'Ana Torres',   'alumno1@utp.edu.pe', '$2a$10$xZiAvKG.YU0AAi0e/IHxueJoAFWMBhx7SHgmeznPTgWPrxdn.onH.', 'Ingeniería de Software', 5, 'ESTUDIANTE'),
(3, 'Diego Rojas',  'alumno2@utp.edu.pe', '$2a$10$xZiAvKG.YU0AAi0e/IHxueJoAFWMBhx7SHgmeznPTgWPrxdn.onH.', 'Ingeniería de Sistemas', 4, 'ESTUDIANTE'),
(4, 'Camila Flores','alumno3@utp.edu.pe', '$2a$10$xZiAvKG.YU0AAi0e/IHxueJoAFWMBhx7SHgmeznPTgWPrxdn.onH.', 'Ingeniería Industrial', 3, 'ESTUDIANTE'),
(5, 'Mateo Castro', 'alumno4@utp.edu.pe', '$2a$10$xZiAvKG.YU0AAi0e/IHxueJoAFWMBhx7SHgmeznPTgWPrxdn.onH.', 'Informática', 7, 'ESTUDIANTE');

-- ============ CURSOS (códigos de ejemplo) ============
INSERT IGNORE INTO cursos (id, codigo, nombre) VALUES
(1, 'SIS-201', 'Algoritmos y Estructuras de Datos'),
(2, 'MAT-101', 'Cálculo I'),
(3, 'SIS-301', 'Base de Datos'),
(4, 'SOF-401', 'Desarrollo Web Integrado');

-- ============ DOCENTES (nombres de ejemplo) ============
INSERT IGNORE INTO docentes (id, nombre, facultad, sede, verificado, estilo_ensenanza, estilo_evaluacion, resumen, etiquetas) VALUES
(1, 'Carlos Ramírez Salazar', 'Facultad de Ingeniería · Ingeniería de Sistemas', 'UTP Lima Centro · Edificio Arequipa', 1,
    'Explicación clara', 'Prácticas y exámenes',
    'Explica los conceptos paso a paso y los conecta con ejercicios similares a las evaluaciones. Las clases son participativas; practicar semanalmente ayuda a llegar con confianza a los exámenes.',
    'Metodología clara,Práctica semanal'),
(2, 'María Fernanda Paredes', 'Facultad de Ingeniería · Ingeniería Industrial', 'UTP Lima Centro · Edificio Arequipa', 1,
    'Evaluación continua', 'Controles semanales y proyecto',
    'Evalúa de forma continua con controles cortos, lo que evita acumular contenido. Muy accesible para resolver dudas dentro y fuera de clase.',
    'Evaluación continua,Accesible'),
(3, 'Jorge Luis Quispe', 'Facultad de Ingeniería · Ingeniería de Sistemas', 'UTP Lima Centro · Edificio Arequipa', 1,
    'Alto nivel de exigencia', 'Exámenes y casos complejos',
    'Domina el tema y exige mucho. Sus exámenes incluyen casos complejos, por lo que conviene estudiar con anticipación y no dejar dudas para el final.',
    'Alto nivel de exigencia,Dominio del tema'),
(4, 'Rosa Elena Mendoza', 'Facultad de Ingeniería · Ingeniería Industrial', 'UTP Lima Centro · Edificio Arequipa', 1,
    'Explicación dinámica', 'Prácticas calificadas',
    'Usa ejemplos cotidianos y dinámicas en clase para explicar los temas. Ideal para quienes sienten que Cálculo es difícil de entender.',
    'Explicación dinámica,Ejemplos reales'),
(5, 'Luis Alberto Torres', 'Facultad de Ingeniería · Ingeniería de Sistemas', 'UTP Lima Centro · Edificio Arequipa', 1,
    'Explicación clara', 'Laboratorios y examen final',
    'Combina teoría breve con laboratorios guiados. Pide entregas puntuales y revisa el avance de cada práctica.',
    'Metodología clara,Laboratorios guiados'),
(6, 'Ana Lucía Vega', 'Facultad de Ingeniería · Ingeniería de Software', 'UTP Lima Centro · Edificio Arequipa', 1,
    'Explicación dinámica', 'Proyecto por entregas',
    'El curso se trabaja con un proyecto por entregas. Da retroalimentación frecuente y fomenta el trabajo en equipo.',
    'Aprendizaje por proyecto,Retroalimentación');

-- ============ QUÉ DOCENTE DICTA QUÉ CURSO ============
INSERT IGNORE INTO docente_curso (id, docente_id, curso_id) VALUES
(1, 1, 1), (2, 3, 1),   -- Algoritmos: Ramírez y Quispe (comparador)
(3, 2, 2), (4, 4, 2),   -- Cálculo I: Paredes y Mendoza (comparador)
(5, 5, 3),
(6, 6, 4);

-- ============ RESEÑAS ============
-- Todas APROBADAS, salvo la 4 que queda PENDIENTE para probar la moderación
INSERT IGNORE INTO resenas (id, usuario_id, docente_id, curso_id, dominio, claridad, empatia, dificultad, comentario, estado, fecha) VALUES
(1,  2, 1, 1, 5, 5, 4, 'MODERADO', 'Los ejemplos de clase se parecen bastante a los ejercicios de evaluación. Explica con paciencia y responde las dudas; practicar por cuenta propia es importante.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 14 DAY)),
(2,  3, 1, 1, 5, 5, 5, 'MODERADO', 'Muy claro al explicar estructuras de datos. Las clases son organizadas y la exigencia es justa si practicas cada semana.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 20 DAY)),
(3,  4, 1, 1, 4, 5, 4, 'MODERADO', 'Buen docente, explica paso a paso. Conviene resolver los ejercicios antes de cada práctica calificada.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 25 DAY)),
(4,  5, 1, 1, 4, 4, 4, 'MODERADO', 'Recién terminé el curso. Me pareció ordenado, aunque algunas semanas el ritmo fue rápido para mí.', 'PENDIENTE', DATE_SUB(NOW(), INTERVAL 1 DAY)),
(5,  2, 2, 2, 5, 4, 5, 'LIGERO',   'Los controles cortos ayudan a no acumular temas. Siempre disponible para resolver dudas después de clase.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 10 DAY)),
(6,  3, 2, 2, 4, 4, 5, 'MODERADO', 'Muy amable y accesible. Hay que estar al día porque evalúa casi todas las semanas.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 18 DAY)),
(7,  5, 2, 2, 5, 5, 4, 'MODERADO', 'Explica con calma y repite lo que haga falta. El proyecto final requiere tiempo, pero se entiende bien.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 30 DAY)),
(8,  4, 3, 1, 5, 3, 3, 'EXIGENTE', 'Sabe muchísimo, pero explica rápido y sus exámenes son difíciles. Hay que estudiar con bastante anticipación.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 12 DAY)),
(9,  5, 3, 1, 4, 3, 2, 'EXIGENTE', 'Nivel de exigencia muy alto. Si no entiendes algo en clase, toca buscar ayuda por tu cuenta.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 22 DAY)),
(10, 3, 4, 2, 4, 5, 5, 'LIGERO',   'Hace que Cálculo se sienta más fácil con ejemplos de la vida diaria. Las clases son dinámicas.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 9 DAY)),
(11, 4, 4, 2, 4, 4, 5, 'LIGERO',   'Muy empática y paciente. Sus prácticas calificadas son parecidas a lo visto en clase.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 16 DAY)),
(12, 2, 5, 3, 5, 4, 4, 'MODERADO', 'Los laboratorios guiados ayudan mucho. Es estricto con las fechas de entrega de cada práctica.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 11 DAY)),
(13, 5, 5, 3, 4, 5, 4, 'MODERADO', 'Teoría breve y mucha práctica. Se aprende bien si asistes a todos los laboratorios.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 28 DAY)),
(14, 3, 6, 4, 5, 5, 5, 'MODERADO', 'Da retroalimentación en cada entrega del proyecto y se nota que le importa que aprendamos.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 8 DAY)),
(15, 4, 6, 4, 5, 4, 5, 'MODERADO', 'El trabajo en equipo es clave en su curso. Explica bien y está pendiente del avance de cada grupo.', 'APROBADA',  DATE_SUB(NOW(), INTERVAL 15 DAY));