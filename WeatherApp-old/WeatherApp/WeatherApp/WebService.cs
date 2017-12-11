using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WeatherApp
{
    class WebService
    {

        public WebService() {

        }

        public async Task<Weather> GetWeatherAsync(String city)
        {
            var client = new System.Net.Http.HttpClient();
            client.BaseAddress = new Uri("http://api.apixu.com/v1/");

            var response = await client.GetAsync("current.json?key=30463600dbcf4b45873192147170412&q="+city);

            var weatherJson = response.Content.ReadAsStringAsync().Result;

            var weather = JsonConvert.DeserializeObject<Weather>(weatherJson);

            return weather;
        }

    }
}
