using SQLite;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WeatherApp
{
    public class City
    {
        private int id;
        private String name;
        private String country;
        private bool favorite;

        [PrimaryKey, AutoIncrement]
        public int ID
        {
            get { return id; }
            
        }
        public string Name
        {
            get { return name; }
            set { name=value; }
        
        }
        public String Country {
            get { return country; }
            set { country = value; }

        }
        public bool Favorite {
               get { return favorite; }
            set { favorite = value; }
        }

    }
}
