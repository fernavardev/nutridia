package com.example.nutridia

// repository que centraliza la coleccion de recetas de la aplicacion
class RecetaRepository {

    // coleccion de objetos de receta con la minuta semanal
    companion object {
        val recetas: List<Receta> = listOf(
            Receta(
                dia = "Lunes",
                nombre = "Pollo con arroz y verduras",
                ingredientes = listOf(
                    "Pechuga de pollo",
                    "Arroz",
                    "Zanahoria",
                    "Pimentón",
                    "Brócoli",
                    "Cebolla"
                ),
                preparacion = "1. Cocina el arroz hasta que esté listo.\n2. Cocina el pollo en una sartén junto con las verduras.\n3. Sirve el pollo y las verduras acompañados del arroz.",
                recomendacion = "Una comida equilibrada que combina proteínas, carbohidratos y verduras."
            ),
            Receta(
                dia = "Martes",
                nombre = "Lentejas con verduras",
                ingredientes = listOf(
                    "Lentejas",
                    "Zanahoria",
                    "Cebolla",
                    "Pimentón",
                    "Zapallo",
                    "Ajo"
                ),
                preparacion = "1. Cocina las lentejas en agua hasta que comiencen a ablandarse.\n2. Agrega las verduras picadas y continúa la cocción.\n3. Cocina hasta que las lentejas y verduras estén tiernas y sirve caliente.",
                recomendacion = "Las lentejas aportan proteínas vegetales, fibra y minerales."
            ),
            Receta(
                dia = "Miércoles",
                nombre = "Pescado con papas cocidas",
                ingredientes = listOf(
                    "Filete de pescado",
                    "Papas",
                    "Lechuga",
                    "Tomate",
                    "Limón",
                    "Condimentos"
                ),
                preparacion = "1. Cocina las papas en agua hasta que estén blandas.\n2. Cocina el pescado a la plancha y prepara una ensalada de lechuga y tomate.\n3. Sirve el pescado acompañado de las papas y la ensalada.",
                recomendacion = "El pescado es una buena fuente de proteínas y grasas saludables."
            ),
            Receta(
                dia = "Jueves",
                nombre = "Pasta con salsa de tomate",
                ingredientes = listOf(
                    "Pasta",
                    "Tomate",
                    "Cebolla",
                    "Zanahoria",
                    "Ajo",
                    "Condimentos"
                ),
                preparacion = "1. Cocina la pasta según las indicaciones hasta que esté lista.\n2. Prepara una salsa cocinando el tomate junto con la cebolla, zanahoria y ajo.\n3. Mezcla la pasta con la salsa y sirve.",
                recomendacion = "Complementar la pasta con verduras ayuda a obtener una comida más equilibrada."
            ),
            Receta(
                dia = "Viernes",
                nombre = "Ensalada de pollo",
                ingredientes = listOf(
                    "Pechuga de pollo",
                    "Lechuga",
                    "Tomate",
                    "Zanahoria",
                    "Pepino",
                    "Limón"
                ),
                preparacion = "1. Cocina la pechuga de pollo y córtala en trozos.\n2. Lava y corta las verduras para preparar la ensalada.\n3. Incorpora el pollo, mezcla los ingredientes y aliña con limón.",
                recomendacion = "Una alternativa ligera que combina proteínas con una variedad de verduras."
            )
        )
    }
}