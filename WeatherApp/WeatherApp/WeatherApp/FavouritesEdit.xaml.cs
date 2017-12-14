using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;
using WeatherApp.ViewModels;

namespace WeatherApp
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class FavouritesEdit : ContentPage
    {
        public FavouritesEdit(List<SelectableData<string>> data)
        {
            InitializeComponent();
            BindingContext = new FavouritesEditViewModel(data);

            ListFav.ItemTapped += (object sender, ItemTappedEventArgs e) => {
                if (e.Item == null) return;
                ((ListView)sender).SelectedItem = null;
            };
        }

    }
}