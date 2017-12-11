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
    }

}
