using System.Collections.Generic;
using System.Linq;
using System.Windows.Input;
using Xamarin.Forms;
using WeatherApp.Helpers;
using System;

namespace WeatherApp.ViewModels
{
    public class FavouritesViewModel : BaseViewModel
    {
        // This is just as an example. Do not create a static property in your ViewModel, for other ViewModels to communicate with, in a production app.
        public static List<SelectableData<string>> SelectedData { get; set; }

        public List<SelectableData<string>> DataSource { get; set; }

        public FavouritesViewModel()
        {
            string favouriteCities = Settings.FavouriteCitiesSettings;
            string allCities = Settings.AllCitiesSettings;
            List<string> favCitiesList = new List<string>(favouriteCities.Split(','));
            List<string> allCitiesList = new List<string>(allCities.Split(','));
            SelectedData = new List<SelectableData<string>>();

            foreach (string city in allCitiesList) {
                if (favCitiesList.Contains(city))
                    SelectedData.Add(new SelectableData<string>() { Data = city, Selected = true});
                else
                    SelectedData.Add(new SelectableData<string>() { Data = city, Selected = false });

            };
               
        }

        public void OnAppearing()
        {
            DataSource = SelectedData.Where(x => x.Selected).ToList();
            OnPropertyChanged(nameof(DataSource));
        }

        public string AllCities
        {
            get
            {
                return Settings.AllCitiesSettings;
            }
            set
            {
                Settings.AllCitiesSettings = value;
                OnPropertyChanged();
            }
        }

        public string FavouriteCities
        {
            get
            {
                return Settings.FavouriteCitiesSettings;
            }
            set
            {
                Settings.FavouriteCitiesSettings = value;
                OnPropertyChanged();
            }
        }


        public ICommand SelectCommand
        {
            get
            {
                return new Command(() =>
                {
                    App.Current.MainPage.Navigation.PushModalAsync(new FavouritesEdit(SelectedData));
                    
                });
            }

        }

    }
}