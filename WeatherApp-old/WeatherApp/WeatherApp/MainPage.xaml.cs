using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace WeatherApp
{
    public partial class MainPage : ContentPage
    {
        public MainPage(WeatherDatabase d)
        {
            InitializeComponent();
            var list = new ListView();
            //ideia é entrar e aparecer as listview em q cada cena é uma cidade favorita, devem ser clickable assim obtens o tempo dessa cidade
            //depois um botão para adicionar favoritos
            Task<List <City>>l=  d.GetItemsNotDoneAsync();
            ObservableCollection<String> CitiesList = new ObservableCollection<String>();
            list.ItemsSource = CitiesList;
            for (int i=0;i<l.Result.Count();i++) {
                CitiesList.Add(l.Result[0].Name);
            }
            
        }
    }
}
