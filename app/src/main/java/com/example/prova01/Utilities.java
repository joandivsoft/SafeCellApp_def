package com.example.prova01;

import com.example.prova01.R;

public class Utilities {

    static final String[] msg_ca={
            "Benvingut/da a l'aplicació SafeCellApp",
            "Sessió iniciada",
            "(L'aplicació està funcionant...)",
            "Compte",
            "Geolocalització",
            "Connexions",
            "Mòbil",
            "Lectures",
            "Latitud",
            "Longitud",
            "Altitud",
            "Connexió xarxa",
            "Mòbil",
            "Lectura WIFI",
            "Id dispositiu",
            "Temps actualització\n(mínim 10 s)",
            "ATURA",
            "REINICIA",
            "Administrador",
            "usuari",
            ""
    };
    static final String[] msg_es={
            "Bienvenido/da a la aplicación SafeCellApp",
            "Sessión iniciada",
            "(La aplicación está corriendo...)",
            "Advertencia",
            "Geolocalización",
            "Conexiones",
            "Móvil",
            "Lecturas",
            "Latitud",
            "Longitud",
            "Altitud",
            "Connexión red",
            "Móvil",
            "Lectura WIFI",
            "Id dispositivo",
            "Tiempo actualitzación\n(mínim 10 s)",
            "PARADA",
            "REINICIA",
            "Administrador",
            "usuario",
            ""
    };
    static final String[] msg_en={
            "Wellcome at SafeCellApp",
            "Session initied",
            "(App is running...)",
            "Warning",
            "Geolocalization",
            "Connections",
            "Mobile",
            "Read data",
            "Latitude",
            "Longitude",
            "Altitude",
            "Net connection",
            "Mobile",
            "WIFI lecture",
            "Id device",
            "Time refresh\n(at least 10 s)",
            "STOP",
            "RESTART",
            "Administrator",
            "User",
            ""
    };

    //15
    static String[][] lang1={msg_ca, msg_es, msg_en};
    static int language1=0;


    //static String getToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjdiYWU4OTVhZmMwYmVhODFmMmZmMDY1ZjY1NjQwNjIzMmEwMTE4YWUyZTcyMDkyNzFjZWE4NDY0ZGUzM2U3ZGIwOTRkNjg4N2VlNTI5ZTk5In0.eyJhdWQiOiI5IiwianRpIjoiN2JhZTg5NWFmYzBiZWE4MWYyZmYwNjVmNjU2NDA2MjMyYTAxMThhZTJlNzIwOTI3MWNlYTg0NjRkZTMzZTdkYjA5NGQ2ODg3ZWU1MjllOTkiLCJpYXQiOjE1NTc3NjA2MzIsIm5iZiI6MTU1Nzc2MDYzMiwiZXhwIjoxNTg5MzgzMDMyLCJzdWIiOiI1Iiwic2NvcGVzIjpbXX0.XSmXY6Gnt0Yckw-8tcbqCiSIT46mjXumz-FAL79Dx3tICZo9lj0GIVuk8JEvPQEqWTX3oJzzV4NSa94TJ4Lovq1lA2x9WIZ7yUTxH6iFdOL6j3Ri8gNcyJ8K6lRSL7NQC2i4vNItPNBrTApqxTFup-axcygG5a_O4aaH22M_9L7dgk4h0T8KVbcivrci_oTD_Dq0ndS8JSQ8YRRAmrNU356-3v_X-FC7MsukTIPOXxhOKCaDfhAOGa2ezwGQerePM90OVhrY74zmlov_CHb3TMxia9c8CsMQnsOAbPB6c6JZRrvkguGSlaDH7AGaLmnbOxWfYMocGbO5K382jZp-WjC1HzBtHuSZgJIAU84JQWWTX9OHP4oJ_PJxo37sov6KVDz_Z7iQhgZGstLgi2SBcg5lR-wz6uk-Hp-LWYUcXXOf0RkgqQCF0jZqD8CzeBdLowTm7EBqz6DTJFyjquoghnLONrmrnndDvQSkJQedW0h7fDUHYRZ7E66TmFEwYCPdnncaANCSaNDG9F298dgDCS9DZVsUeVeJoUbvdheSyLmhdI_GAYCfXUDEFFfLC-6KQkIBIlCsw67uFYOn0e5dr0FqdVWAf6KxrNfR7_IjD2IEyVZDJPLahPfgrNK8Ms8PjZC9TWVu1ffG4cYpNXw5lKPZn5vjDcOKg40GtlYkj38";
    //static String getToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijk0ZWEwY2NhY2Y0Mzk2NjE0ZWRhN2FiMmJjYzg1YmYwNjI4NjJkOWVhOWJiYWZmZTJmNTQxYTM2MTMyNmJkMjBjMmEyMTFlYWYwODljNGQyIn0.eyJhdWQiOiI5IiwianRpIjoiOTRlYTBjY2FjZjQzOTY2MTRlZGE3YWIyYmNjODViZjA2Mjg2MmQ5ZWE5YmJhZmZlMmY1NDFhMzYxMzI2YmQyMGMyYTIxMWVhZjA4OWM0ZDIiLCJpYXQiOjE1NTc3NjU4NzIsIm5iZiI6MTU1Nzc2NTg3MiwiZXhwIjoxNTg5Mzg4MjcyLCJzdWIiOiI1Iiwic2NvcGVzIjpbXX0.vsuONouIBP6lkOV3zncbVDCDDfak7xuRqg1cMvbrmoprKGpbDblspkkT7WewDq1ls18FZRJzctDsDM8U9udqRrkwIMU4PbT0zj18CJdBj01HMCoPLZcN4xYt0keTUzLSnsWH35s6ipRSV2eYCvOpW3klaOcFOqesuY7OKJhQe17xYfU8FwDyuFgjNSSklpnGtFQoBTgqqfjiyL74FyXVmF6zQjiWw1ydCJtfHKp1inv46p84zH_N21hzaeRXrHzpVkqn8-da9XHFI4RDSFHM04OcwUsRhKce_L1IpliNXk9ovG_-qZKfWdtA7eBhSkl_3Fx2EZUy_8odrQwNdeeE-GTOHxzYOYjB4EVEnDe2IuQfVBDgX5u-MzqVZdAJxouf3o8CXrDTIktCXh7RFx-MRg1oMMFTeZrk7xFBkj7Y4ak9wgHZhXRK49-ZUSFWeJut1Ajn0U-t2lvN--cfX8cmttDI0P0IH3n2e9Axxu4dlm1ttL4N-3xp1pdqT2G6V8nJkW9fYqPZGWhvdpydHjR1_4grgJYJQfwY-F-gh9KdNjCFo5hjJapLryydWnUVXnxqp8VXqqwYz_C1cwijdE3RD75DEsrs7S5YppBxs04fhTMvQSl_AvIGc9jUfUmNaF73u1jrN1rLQ_evA7S-nGBcj5htZQNZrQvk-wnDsEG5Pjk";
    static String getToken="";
    static String getInternet="";
    static String getWifi="";
    static int idDevice=0;
    static String numIMEI="860921033593787"; // local
    static String numIMEI_def="0";  //Definitiu (local o real)
    static String pwdScreen="123";
    static String permisos="false";

    //https://safe-cell.herokuapp.com/dashboard
    //https://safe-cell.herokuapp.com/api/device/getDeviceByImei/{IMEI} >> Aconsegueixo id disp.
    //https://safe-cell.herokuapp.com/api/service/getStatuses/{ID}  >> Per veure com estan els serveis
    //https://safe-cell.herokuapp.com/api/device/22
    //api/device/getDeviceByImei/{IMEI} >> Aconsegueixo id disp.
    //api/service/getStatuses/{ID}  >> Per veure com estan els serveis
    //https://safe-cell.herokuapp.com/api/device/getDeviceIdByImei/860921033593787

}
