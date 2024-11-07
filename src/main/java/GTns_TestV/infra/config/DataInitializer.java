package GTns_TestV.infra.config;

import GTns_TestV.model.entity.Carrera;
import GTns_TestV.model.enums.ChasideCategory;
import GTns_TestV.infra.repository.CarreraRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(CarreraRepository carreraRepository) {
        return args -> {
            List<Carrera> carreras = Arrays.asList(
                    new Carrera(null, "Administración de Empresas", "Desarrolla habilidades para liderar y gestionar negocios exitosos, convirtiéndote en un profesional capaz de tomar decisiones estratégicas en entornos empresariales.", ChasideCategory.C, null),
                    new Carrera(null, "Gestión de Proyectos", "Conviértete en un experto en planificar, coordinar y controlar proyectos, asegurando su éxito desde la idea inicial hasta la ejecución.", ChasideCategory.C, null),
                    new Carrera(null, "Análisis Financiero", "Aprende a analizar datos financieros para guiar decisiones clave, contribuyendo a la estabilidad y crecimiento de las organizaciones.", ChasideCategory.C, null),
                    new Carrera(null, "Contabilidad", "Domina la técnica de registrar y analizar la situación financiera de empresas, siendo un pilar fundamental para su organización y éxito.", ChasideCategory.C, null),
                    new Carrera(null, "Marketing", "Crea estrategias innovadoras que impacten en el mercado y conviertan productos en experiencias que conecten con los consumidores.", ChasideCategory.C, null),
                    new Carrera(null, "Psicología", "Explora la mente humana y contribuye al bienestar emocional, apoyando a las personas en su desarrollo y adaptación.", ChasideCategory.H, null),
                    new Carrera(null, "Trabajo Social", "Interviene en comunidades para generar un cambio positivo y significativo, promoviendo el bienestar social y la justicia.", ChasideCategory.H, null),
                    new Carrera(null, "Sociología", "Comprende y analiza la estructura de la sociedad para influir en políticas y mejorar la calidad de vida de las personas.", ChasideCategory.H, null),
                    new Carrera(null, "Filosofía", "Reflexiona sobre los grandes interrogantes de la existencia, desarrollando un pensamiento crítico y una visión profunda del ser humano.", ChasideCategory.H, null),
                    new Carrera(null, "Antropología", "Estudia al ser humano en su diversidad cultural, explorando las raíces y evolución de nuestra sociedad.", ChasideCategory.H, null),
                    new Carrera(null, "Derecho", "Defiende la justicia y el orden social mediante el estudio y aplicación de las leyes, convirtiéndote en un agente de cambio.", ChasideCategory.H, null),
                    new Carrera(null, "Bellas Artes", "Expresa tu creatividad a través de diversas disciplinas artísticas y contribuye al enriquecimiento cultural.", ChasideCategory.A, null),
                    new Carrera(null, "Diseño Gráfico", "Comunica ideas y mensajes visuales que impactan en la sociedad, desarrollando la habilidad de crear imágenes memorables.", ChasideCategory.A, null),
                    new Carrera(null, "Diseño de Interiores", "Transforma espacios en lugares funcionales y estéticamente atractivos, mejorando la experiencia de quienes los habitan.", ChasideCategory.A, null),
                    new Carrera(null, "Música", "Explora el arte de la interpretación y la creación musical, llevando inspiración y emociones a las personas.", ChasideCategory.A, null),
                    new Carrera(null, "Multimedia", "Crea contenidos que combinan texto, audio y video para contar historias y transmitir mensajes de forma atractiva.", ChasideCategory.A, null),
                    new Carrera(null, "Comunicación Audiovisual", "Domina la producción y análisis de contenido audiovisual, transmitiendo mensajes visuales que impactan y comunican.", ChasideCategory.A, null),
                    new Carrera(null, "Medicina", "Dedica tu vida a la ciencia de la salud, cuidando y curando a las personas para mejorar su calidad de vida.", ChasideCategory.S, null),
                    new Carrera(null, "Enfermería", "Brinda cuidado y apoyo a pacientes en su recuperación y bienestar, siendo una pieza clave en el sistema de salud.", ChasideCategory.S, null),
                    new Carrera(null, "Farmacia", "Estudia los medicamentos y su impacto en la salud, ayudando a mejorar la vida de las personas mediante la ciencia.", ChasideCategory.S, null),
                    new Carrera(null, "Terapia Ocupacional", "Apoya a las personas en su adaptación y recuperación para llevar una vida plena y funcional en sus actividades diarias.", ChasideCategory.S, null),
                    new Carrera(null, "Psicología Clínica", "Ayuda a personas con trastornos mentales, promoviendo el bienestar emocional y una vida equilibrada.", ChasideCategory.S, null),
                    new Carrera(null, "Nutrición", "Comprende cómo la alimentación impacta en la salud y bienestar, guiando a otros hacia un estilo de vida saludable.", ChasideCategory.S, null),
                    new Carrera(null, "Ingeniería en Sistemas", "Desarrolla y mantiene sistemas informáticos, contribuyendo a la innovación y eficiencia tecnológica.", ChasideCategory.I, null),
                    new Carrera(null, "Redes y Seguridad Informática", "Protege redes y sistemas informáticos, asegurando la seguridad en la era digital.", ChasideCategory.I, null),
                    new Carrera(null, "Desarrollo de Software", "Crea y mejora software que transforma la vida de las personas y la eficiencia de las empresas.", ChasideCategory.I, null),
                    new Carrera(null, "Automatización y Robótica", "Diseña sistemas automatizados que mejoran procesos y aumentan la productividad en diversas industrias.", ChasideCategory.I, null),
                    new Carrera(null, "Tecnología de la Información", "Gestiona sistemas de información, asegurando la integridad y eficiencia de los datos en la organización.", ChasideCategory.I, null),
                    new Carrera(null, "Mantenimiento Industrial", "Garantiza el funcionamiento óptimo de maquinaria y equipos, siendo crucial para la productividad industrial.", ChasideCategory.I, null),
                    new Carrera(null, "Biología", "Estudia la vida y sus interacciones, contribuyendo al conocimiento de los seres vivos y sus ecosistemas.", ChasideCategory.E, null),
                    new Carrera(null, "Química", "Explora la composición y propiedades de la materia, aplicando este conocimiento en la creación de productos útiles.", ChasideCategory.E, null),
                    new Carrera(null, "Física", "Comprende el comportamiento de la materia y la energía, aplicando estos conocimientos en innovaciones científicas.", ChasideCategory.E, null),
                    new Carrera(null, "Ciencias Ambientales", "Investiga la relación entre humanos y medio ambiente, promoviendo un desarrollo sostenible.", ChasideCategory.E, null),
                    new Carrera(null, "Ciencias de Datos", "Analiza grandes volúmenes de datos para tomar decisiones informadas y estratégicas.", ChasideCategory.E, null),
                    new Carrera(null, "Biotecnología", "Utiliza organismos vivos para desarrollar productos y soluciones tecnológicas que mejoran la vida.", ChasideCategory.E, null),
                    new Carrera(null, "Educación Primaria", "Forma a los futuros ciudadanos desde sus primeros pasos en la educación, sentando las bases de su desarrollo.", ChasideCategory.E, null),
                    new Carrera(null, "Educación Secundaria", "Guía a jóvenes en una etapa crucial, preparándolos para sus futuros académicos y profesionales.", ChasideCategory.E, null),
                    new Carrera(null, "Educación Especial", "Enseña y apoya a alumnos con discapacidades, promoviendo la inclusión y el desarrollo personal.", ChasideCategory.E, null),
                    new Carrera(null, "Pedagogía", "Estudia la educación y sus métodos, transformando el proceso de enseñanza y aprendizaje.", ChasideCategory.E, null),
                    new Carrera(null, "Formación Profesional", "Desarrolla habilidades prácticas y especializadas que abren puertas en el mercado laboral.", ChasideCategory.E, null),
                    new Carrera(null, "Ciencias Policiales", "Aporta a la seguridad y justicia, protegiendo a la sociedad mediante la aplicación del conocimiento policial.", ChasideCategory.D, null),
                    new Carrera(null, "Seguridad Nacional", "Contribuye a la protección de la soberanía y seguridad del país, defendiendo el bienestar colectivo.", ChasideCategory.D, null),
                    new Carrera(null, "Gestión de Emergencias", "Maneja situaciones de crisis, ofreciendo respuestas efectivas en momentos críticos.", ChasideCategory.D, null),
                    new Carrera(null, "Defensa Civil", "Prepárate para prevenir y responder ante desastres, salvaguardando vidas y bienes en emergencias.", ChasideCategory.D, null),
                    new Carrera(null, "Psicología Criminal", "Estudia la mente criminal y contribuye a la prevención del delito mediante el análisis del comportamiento.", ChasideCategory.D, null)
            );

            for (Carrera carrera : carreras) {
                List<Carrera> carrerasExistentes = carreraRepository.findByNombre(carrera.getNombre());
                if (carrerasExistentes.isEmpty()) {
                    carreraRepository.save(carrera);
                    System.out.println("Carrera " + carrera.getNombre() + " registrada en la base de datos.");
                } else {
                    System.out.println("La carrera " + carrera.getNombre() + " ya existe en la base de datos.");
                }
            }
        };
    }
}