package com.rober.utils;

public class AppoitmentUtils {

    // ✅ Horario disponible creado (por el doctor)
    public static final String DISPONIBILIDAD_CREADA_CODE = "001";
    public static final String DISPONIBILIDAD_CREADA_MSG = "Horario disponible creado correctamente.";

    // ❌ Error al crear disponibilidad
    public static final String DISPONIBILIDAD_DUPLICADA_CODE = "002";
    public static final String DISPONIBILIDAD_DUPLICADA_MSG = "Ya existe un horario igual registrado para este doctor.";

    // ✅ Cita agendada (paciente toma un bloque disponible)
    public static final String CITA_CONFIRMADA_CODE = "003";
    public static final String CITA_CONFIRMADA_MSG = "Cita confirmada exitosamente.";

    // ❌ Bloque ya ocupado
    public static final String HORARIO_NO_DISPONIBLE_CODE = "004";
    public static final String HORARIO_NO_DISPONIBLE_MSG = "Este horario ya fue reservado por otro paciente.";

    // ✅ Cita cancelada (por doctor o paciente)
    public static final String CITA_CANCELADA_CODE = "005";
    public static final String CITA_CANCELADA_MSG = "La cita ha sido cancelada.";

    // ❌ Error: no se encontró la cita
    public static final String CITA_NO_ENCONTRADA_CODE = "006";
    public static final String CITA_NO_ENCONTRADA_MSG = "La cita no existe o no se encuentra disponible.";

    // ❌ Error: cita ya en estado no modificable
    public static final String CITA_ESTADO_INVALIDO_CODE = "007";
    public static final String CITA_ESTADO_INVALIDO_MSG = "No se puede modificar una cita con estado CONFIRMADA o CANCELADA.";

    // ✅ Consulta de disponibilidad exitosa
    public static final String DISPONIBILIDAD_LISTADA_CODE = "008";
    public static final String DISPONIBILIDAD_LISTADA_MSG = "Disponibilidad obtenida correctamente.";

    // ✅ Consulta de citas del paciente o doctor
    public static final String CITAS_LISTADAS_CODE = "009";
    public static final String CITAS_LISTADAS_MSG = "Citas obtenidas correctamente.";


    // ❌ Error: No acceso a la cita
    public static final String CITA_NO_ACCESO_CODE = "010";
    public static final String CITA_NO_ACCESO_MSG = "El usuario no tiene acceso a esta cita.";

    // ✅ Cita obtenida
    public static final String CITA_LISTADA_CODE = "011";
    public static final String CITA_LISTADA_MSG = "Cita obtenida correctamente.";

}
