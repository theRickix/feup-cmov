using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.CompilerServices;
using WeatherApp.ServicesHandler;

namespace WeatherApp.ViewModels
{
    class WeatherViewModel : INotifyPropertyChanged
    {

        Services services = new Services();

        private WeatherModel weatherModel;

        public WeatherModel WeatherModel
        {
            get { return weatherModel; }
            set
            {
                weatherModel = value;
                OnPropertyChanged();
            }
        }

        private string city;

        public string City
        {
            get { return city; }
            set
            {
                city = value;
                InitializeGetWeatherAsync();
                OnPropertyChanged();
            }
        }

        private string selectedItem;

        private bool _isBusy;

        public bool IsBusy
        {
            get { return _isBusy; }
            set
            {
                _isBusy = value;
                OnPropertyChanged();
            }
        }


        private async Task InitializeGetWeatherAsync()
        {
            try
            {
                IsBusy = true;
                WeatherModel = await services.GetWeather(city);
            }
            finally
            {
                IsBusy = false;
            }
        }

        public event PropertyChangedEventHandler PropertyChanged;

        protected virtual void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

    }
}
