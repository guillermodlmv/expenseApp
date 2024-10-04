package com.henry.expenseApp.utils.exception.utils;

public class Messages {
    // General
    public static final String SUCCESS = "Ok";
    public static final String BAD_FORMAT_DATE = "Formato de fecha incorrecto. Use el formato 'yyyy/MM/dd'.";
    public static final String EMPTY_DATE = "La fecha no puede ser nula o vacía";

    // User Exception Messages
    public static final String USER_WITHOUT_CATEGORIES = "No tiene categorías registradas.";
    public static final String USER_WITHOUT_EXPENSES = "No tiene gastos registradas.";
    public static final String USERS_NOT_FOUND = "No se encontraron Usuarios con los criterios de búsqueda";
    public static final String USER_NOT_FOUND = "No se encontró Usuario";
    public static final String USER_IS_NEEDED = "El usuario es obligatorio.";
    public static final String INTERNAL_SERVER_ERROR = "Error no previsto.";
    public static final String BAD_REQUEST = "Peticion mal formulada.";


    // Category Exception Messages
    public static final String CATEGORY_NOT_FOUND = "La categoría no fue encontrada.";
    public static final String CATEGORY_NOT_BELONGS_USER = "La categoría no pertenece a este usuario.";


    // Expenses Exception Messages
    public static final String EXPENSE_NOT_FOUND = "El gasto no fue encontrado.";
    public static final String EMPTY_EXPENSE = "El gasto es obligatorio.";
    public static final String EXPENSE_DATE_EMPTY = "La fecha para el gasto no puede ser vacío.";
    public static final String EXPENSE_DESCRIPTION_EMPTY = "La descripción para el gasto no puede ser vacío.";
    public static final String EXPENSE_NEGATIVE_AMOUNT = "El monto no puede ser menor que 0.";
    public static final String CREATE_EXPENSE_INTERNAL_SERVER_ERROR = "Ocurrió un error inesperado al crear el gasto";
    public static final String ALL_EXPENSES_INTERNAL_SERVER_ERROR = "Ocurrió un error inesperado al recuperar los gastos";
    public static final String EXPENSE_BY_ID_INTERNAL_SERVER_ERROR = "Ocurrió un error inesperado al recuperar el gasto";
    public static final String UPDATE_EXPENSE_INTERNAL_SERVER_ERROR = "Ocurrió un error inesperado al actualizar el gasto";
    public static final String DELETE_EXPENSE_INTERNAL_SERVER_ERROR = "Ocurrió un error inesperado al eliminar el gasto";

    // General Exception Messages
    public static final String GENERAL_NO_CACHED_ERROR = "Ocurrió una excepción no controlada al ";
    public static final String GENERAL_OUT_OF_RANGE_ERROR = "Su selección es invalida, por favor vuelve a intentarlo";
    public static final String GENERAL_INPUT_MUST_BE_NUMBER = "Por favor selecciones una opción numerica.";
    public static final String GENERAL_INVALID_INDEX = "El id seleccionado es invalido";
    public static final String ELEMENT_NOT_FOUND = "El elemento no fue encontrado.";
    public static final String INITIALIZE_TABLES_SERVER_ERROR = "Ocurrió un error inesperado al inicializar bases de datos.";
}
