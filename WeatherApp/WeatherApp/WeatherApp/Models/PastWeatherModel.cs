using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WeatherApp.Models
{
    public class Location
    {
        public string name { get; set; }
        public string region { get; set; }
        public string country { get; set; }
        public double lat { get; set; }
        public double lon { get; set; }
        public string tz_id { get; set; }
        public int localtime_epoch { get; set; }
        public string localtime { get; set; }
    }

    public class Condition
    {
        public string text { get; set; }
        public string icon { get; set; }
        public int code { get; set; }
    }

    public class Day
    {
        public double maxtemp_c { get; set; }
        public double maxtemp_f { get; set; }
        public double mintemp_c { get; set; }
        public double mintemp_f { get; set; }
        public double avgtemp_c { get; set; }
        public double avgtemp_f { get; set; }
        public double maxwind_mph { get; set; }
        public double maxwind_kph { get; set; }
        public double totalprecip_mm { get; set; }
        public double totalprecip_in { get; set; }
        public double avgvis_km { get; set; }
        public double avgvis_miles { get; set; }
        public double avghumidity { get; set; }
        public Condition condition { get; set; }
        public double uv { get; set; }
    }

    public class Astro
    {
        public string sunrise { get; set; }
        public string sunset { get; set; }
        public string moonrise { get; set; }
        public string moonset { get; set; }
    }

    public class Hour
    {
        public int time_epoch { get; set; }
        public string time { get; set; }
        public double temp_c { get; set; }
        public double temp_f { get; set; }
        public int is_day { get; set; }
        public Condition condition { get; set; }
        public double wind_mph { get; set; }
        public double wind_kph { get; set; }
        public int wind_degree { get; set; }
        public string wind_dir { get; set; }
        public double pressure_mb { get; set; }
        public double pressure_in { get; set; }
        public double precip_mm { get; set; }
        public double precip_in { get; set; }
        public int humidity { get; set; }
        public int cloud { get; set; }
        public double feelslike_c { get; set; }
        public double feelslike_f { get; set; }
        public double windchill_c { get; set; }
        public double windchill_f { get; set; }
        public double heatindex_c { get; set; }
        public double heatindex_f { get; set; }
        public double dewpoint_c { get; set; }
        public double dewpoint_f { get; set; }
        public int will_it_rain { get; set; }
        public string chance_of_rain { get; set; }
        public int will_it_snow { get; set; }
        public string chance_of_snow { get; set; }
        public double vis_km { get; set; }
        public double vis_miles { get; set; }
    }

    public class Forecastday
    {
        public string date { get; set; }
        public int date_epoch { get; set; }
        public Day day { get; set; }
        public Astro astro { get; set; }
        public List<Hour> hour { get; set; }
    }

    public class Forecast
    {
        public List<Forecastday> forecastday { get; set; }
    }

    public class PastWeatherModel
    {
        public Location location { get; set; }
        public Forecast forecast { get; set; }

        public List<Hour> NeededHours
        {   
            get
            {
                List<Hour> list = new List<Hour>();
                list.Add(this.forecast.forecastday[0].hour[0]);
                list.Add(this.forecast.forecastday[0].hour[3]);
                list.Add(this.forecast.forecastday[0].hour[6]);
                list.Add(this.forecast.forecastday[0].hour[9]);
                list.Add(this.forecast.forecastday[0].hour[12]);
                list.Add(this.forecast.forecastday[0].hour[15]);
                list.Add(this.forecast.forecastday[0].hour[18]);
                list.Add(this.forecast.forecastday[0].hour[21]);
                return list;
            }
            
        }
        public string CompleteLocation
        {
            get { return this.location.name + ", " + this.location.country; }
        }

        public string DisplayedImageDay
        {
            get { return "http:" + this.forecast.forecastday[0].day.condition.icon; }
        }

        public string TempCelsiusAvg
        {
            get { return this.forecast.forecastday[0].day.avgtemp_c.ToString(); }
        }

        public string MinTemp
        {
            get { return this.forecast.forecastday[0].day.mintemp_c.ToString()+"ºC"; }
        }
        public string MaxTemp
        {
            get { return this.forecast.forecastday[0].day.maxtemp_c.ToString()+"ºC"; }
        }

        public string MaxWind
        {
            get { return this.forecast.forecastday[0].day.maxwind_kph.ToString()+" km/h"; }
        }

        public string TotalPrecipitation
        {
            get { return this.forecast.forecastday[0].day.totalprecip_mm.ToString() + " mm"; }
        }

        public string AvgHumidity
        {
            get { return this.forecast.forecastday[0].day.avghumidity.ToString() + "%"; }
        }
    }
}