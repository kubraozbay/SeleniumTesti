import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;


import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Kübra Özbaykus on 10.03.2018.
 */
public class TestCase {

    private WebDriver webDriver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.gecko.driver","/Users/Lenovo/Documents/geckodriver.exe");
        webDriver=new FirefoxDriver();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.get("https://www.n11.com/");
    }


    @Test
    public void doTestCase() throws Exception{



        //Giriş yap sayfasına gidilip o sayfada olunup olunmadığı başlıktan kontrol ediliyor.
        WebElement btnGirisYapaGit=webDriver.findElement(By.className("btnSignIn"));
        btnGirisYapaGit.click();

        if(webDriver.getTitle().equals("Giriş Yap - n11.com")) System.out.println("Giriş sayfası onaylandı.");
        else System.out.println("Bir hata oluştu.");



        //email ve şifre ilgili inputlara yazılıyor.
        WebElement fillEmail=webDriver.findElement(By.id("email"));
        fillEmail.sendKeys("kubraozbaykus@gmail.com");

        WebElement fillSifre=webDriver.findElement(By.id("password"));
        fillSifre.sendKeys("istanbul34");

        //giriş yap butonuna tıklanıyor.
        WebElement btnLogin=webDriver.findElement(By.id("loginButton"));
        btnLogin.click();



        //Eğer anasayfada giriş yap butonu yoksa giriş yapıldığı onaylanıyor.
        Boolean isPresent=webDriver.findElements(By.className("btnSignIn")).size()>0;
        if(isPresent==false) System.out.println("Kullanıcı girişi başarılı");
        else System.out.println("Giriş Başarısız");


        //bazı zamanlar sayfada bir pop-up çıkıyor ve bu arama yapılmasını veya butona basılmasını engelleyebiliyor
        //bu nedenle eğer o pop-up varsa kapatılıyor ve 4 sn bekledikten sonra butona basılması sağlanıyor.
        Boolean popUpVarMi=webDriver.findElements(By.className("sgm-notification-title")).size()>0;
        if(popUpVarMi) {
            WebElement popUpKapat=webDriver.findElement(By.className("seg-popup-close"));
            popUpKapat.click();
            Thread.sleep(4000);


        }

        //arama butonuna basılıyor
        WebElement searchBox=webDriver.findElement(By.id("productSearchForm"));
        searchBox.click();


        //arama alanına samsung yazılıyor
        WebElement fillSearch=webDriver.findElement(By.id("searchData"));
        fillSearch.sendKeys("samsung");


        //Ara butonuna tıklanıyor.
        WebElement araButonu=webDriver.findElement(By.className("searchBtn"));
        araButonu.click();


        //aranan sonuçların samsung olup olmadığı kontrol ediliyor.
        By h1Bul=By.cssSelector("div.resultText >h1");
        WebElement aramaSonucu=webDriver.findElement(h1Bul);
        if(aramaSonucu.getText().equals("Samsung")) System.out.println("Samsung için sonuçlar dönüyor.");
        else System.out.println("Samsung sonuçları dönmüyor.");


        //sayfa numaralarından 2.sayfaya tıklanıyor.
        WebElement sayfa=webDriver.findElement(By.cssSelector("a[href*='https://www.n11.com/arama?q=samsung&pg=2']"));
        sayfa.click();

        WebElement currentPage=webDriver.findElement(By.className("currentPage"));
        if(currentPage.getAttribute("value").equals("2")) System.out.println("2.sayfada olduğunuz onaylandı.");
        else System.out.println("2.sayfada değil.");

        //2.sayfadaki "product name" classının altındaki elemanlar(bulgu sonuçları) bir listeye ekleniyor ve 3.ürün seçiliyor
        List<WebElement> searchResults=webDriver.findElements(By.className("productName"));
        searchResults.get(2).click();
       String urunIsmi= searchResults.get(2).getText();
       System.out.println("Ürün ismi= "+urunIsmi);


       //Bazı ürünlerde renk seçimi gerekiyor. eğer renk seçilmezse sepete ekleme kabul edilmiyor.
        //bu nedenle sayfada bu alanın olup olmadığını kontrol edip en garanti index olan 1 ile işleme devam ettim.
        Boolean renkSecimiVarMi=webDriver.findElements(By.className("skuArea")).size()>0;
        if(renkSecimiVarMi==true) {
            Select renk=new Select(webDriver.findElement(By.tagName("select")));
            renk.selectByIndex(1);
        }




        //ürün sepete ekleniyor.
        WebElement btnSepeteEkle=webDriver.findElement(By.className("btnAddBasket"));
        btnSepeteEkle.click();



        //sepete gidiliyor.
        WebElement btnSepeteGit=webDriver.findElement(By.className("myBasket"));
        btnSepeteGit.click();

        //sepette olup olmadığı titledan kontrol ediliyor.
        if(webDriver.getTitle().equals("Sepetim - n11.com")) System.out.println("Sepetim sayfasında olduğu onaylandı.");
        else System.out.println("Sepetim sayfasında değil");

        //sepetteki tüm ürünler kontrol ediliyor ve
        // ilgili ürünün sepette olup olmadığı önceden tutulan string değerden kontrol ediliyor.
        List<WebElement> sepettekiler=webDriver.findElements(By.className("prodDescription"));
        if(sepettekiler.get(0).getText().equals(urunIsmi))
            System.out.println(urunIsmi+" sepette olduğu onaylandı.");
        else
            System.out.println(urunIsmi+" sepette değil.");


        //ürün favorilere ekleniyor.
        WebElement favorilereEkle=webDriver.findElement(By.className("followBtn"));
        favorilereEkle.click();


        //hesabım sayfasına ulaşabilmek için anasayfaya gidiliyor.
        webDriver.get("https://www.n11.com/");



        //hesabım butonunun alt seçenekleri farenin üzerine gelmesiyle açılıyor
        WebElement hesabım=webDriver.findElement(By.className("myAccount"));
        Actions fareSurukle=new Actions(webDriver).moveToElement(hesabım);
        fareSurukle.build().perform();


        //istek listelerin/favorilerim sekmesine gidiliyor.
        WebElement favorilereGit=webDriver.findElement(By.cssSelector("a[href*='https://www.n11.com/hesabim/istek-listelerim']"));
        favorilereGit.click();


        //favori listesine gidiliyor.
        WebElement favoriListesineGit=webDriver.findElement(By.className("listItemTitle"));
        favoriListesineGit.click();

        //favoriler sayfasında olup olmadığı titledan kontrol ediliyor.
        if(webDriver.getTitle().equals("Favorilerim - n11.com")) System.out.println("Favorilerim sayfasında olduğu onaylandı.");
        else System.out.println("Favorilerim sayfasında değil");



        //favorilere eklediğimiz ürünün listede olup olmadığı ismi sayesinde kontrol ediliyor.
        List<WebElement> favoriIsimleri=webDriver.findElements(By.cssSelector("h3"));
        if(urunIsmi.equals(favoriIsimleri.get(0).getText())) System.out.println(urunIsmi+" ürünün favorilerde olduğu onaylandı.");
        else System.out.println("Ürün favorilerde yok");


        //seçilen en son favori ürün ilk ürün olduğu için listeye atılan favori ürünlerden indexi 0 olan siliniyor.
        List<WebElement> silButonu=webDriver.findElements(By.className("deleteProFromFavorites"));
        silButonu.get(0).click();


        WebElement onaylaButonu=webDriver.findElement(By.className("confirm"));
        onaylaButonu.click();

        //bütün favori isimleri listesi taranarak silinen ürünün listede olup olmadığı kontrol ediliyor.
        int kontrol=0;
        for(int i=0;i<favoriIsimleri.size();i++){

            if(favoriIsimleri.get(i).equals(urunIsmi)){
                System.out.print("Ürün silinirken bir hata oluştu.");
                kontrol=1;
                break;
            }
        }

        if(kontrol==0){
            System.out.println("Ürünün favorilerden silindiği onaylandı.");
        }
   }
   @After
    public void after(){

        webDriver.quit();
   }

}
