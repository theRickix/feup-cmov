using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Input;
using Xamarin.Forms;
using WeatherApp.Helpers;

namespace WeatherApp.ViewModels
{
    public class FavouritesEditViewModel
    {

        public FavouritesEditViewModel(List<SelectableData<string>> data)
        {
            DataList = data;
        }

        // As example if you need to convert
        //private void LoadData(List<ExampleData> data)
        //{
        //	var list = new List<SelectableData<ExampleData>>();

        //	foreach (var item in data)
        //		list.Add(new SelectableData<ExampleData>() { Data = item });

        //	DataList = list;
        //}

        public List<SelectableData<string>> DataList { get; set; }

        public List<SelectableData<string>> GetNewData()
        {
            var list = new List<SelectableData<string>>();

            foreach (var data in DataList)
            {
                list.Add(new SelectableData<string>() { Data = data.Data, Selected = data.Selected });
            }

            string favouriteCities = Settings.FavouriteCitiesSettings;
            List<string> favCitiesList = new List<string>();
            foreach (SelectableData<string> sd in list)
            {
                string s = sd.Data;

                if (sd.Selected)
                {
                    favCitiesList.Add(s);
                }


            }
            Settings.FavouriteCitiesSettings = String.Join(",", favCitiesList);
            System.Diagnostics.Debug.WriteLine(Settings.FavouriteCitiesSettings);


            return list;
        }

        public ICommand FinishCommand
        {
            get
            {

                return new Command(async () =>
                {
                    FavouritesViewModel.SelectedData = GetNewData();
                    
                    await App.Current.MainPage.Navigation.PopModalAsync();
                });
            }

        }

    }
}