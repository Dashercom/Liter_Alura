package Liter_Alura.Liter_Alura.conversor;

public interface IntConversor {
    <T> T obtenerDatos(String json, Class<T> clase);
}
