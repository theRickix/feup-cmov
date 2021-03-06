﻿using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.CompilerServices;
using WeatherApp.ServicesHandler;
using WeatherApp.Helpers;
using Plugin.Connectivity;

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
                IsVisible = true;
                OnPropertyChanged();
            }
        }

        private List<string> _favCities;

        public List<string> Cities
        {
            get
            {
                string favouriteCities = Settings.FavouriteCitiesSettings;
                _favCities = new List<string>(favouriteCities.Split(','));
                return _favCities;
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

        private bool _isVisible;
        public bool IsVisible
        {
            get
            {
                return _isVisible;
            }
            set
            {
                _isVisible = value;
                OnPropertyChanged();
            }
        }


        private async Task InitializeGetWeatherAsync()
        {
            try
            {
                IsBusy = true;

                if (!CrossConnectivity.Current.IsConnected)
                    await App.Current.MainPage.DisplayAlert("Alert", "No internet!", "OK");
                else
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
