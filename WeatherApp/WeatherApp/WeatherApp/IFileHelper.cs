// Necessário para ir buscar a base de dados
namespace WeatherApp {
  public interface IFileHelper {
    string GetLocalFilePath(string filename);
  }
}