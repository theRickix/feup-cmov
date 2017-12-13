using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WeatherApp.RestClient;


namespace WeatherApp.ServicesHandler
{
    class Services
    {
        Apixu<WeatherModel> apixuRest = new Apixu<WeatherModel>();
        public async Task<WeatherModel> GetWeather(string city)
        {
            var getWeather = await apixuRest.GetWeatherAsync(city);
            return getWeather;
        }

        Apixu<PastWeatherModel> apixuRestPast = new Apixu<PastWeatherModel>();
        public async Task<PastWeatherModel> GetPastWeather(string city,DateTime date)
        {
            var getWeather = await apixuRestPast.GetPastWeatherAsync(city,date);
            return getWeather;
        }

        Apixu<FutureWeatherModel> apixuRestFuture = new Apixu<FutureWeatherModel>();
        public async Task<FutureWeatherModel> GetFutureWeather(string city, DateTime date)
        {
            var getWeather = await apixuRestFuture.GetFutureWeatherAsync(city, date);
            return getWeather;
        }
    }

}
