package com.atguigu.cloud.utils;

import java.util.*;

import com.atguigu.cloud.Position;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
/**
 * 岗位数据网站获取
 */
import java.util.*;
import java.util.stream.IntStream;
public class JobDataUtils {
    public static void main(String[] args) {
        position3(null, "401801","短视频策划", "kwEVMOJHKOI5TLCKGI", 1);
    }
    public static List<Position> position3(String city, String cityCode, String name, String positionCode, int count)  {
        try{
            List<String> userAgents = Arrays.asList(
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.3");
            Random random = new Random();
            String userAgent = userAgents.get(random.nextInt(userAgents.size()));

            ChromeOptions options = new ChromeOptions();
            options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
            options.addArguments("--headless", "--disable-gpu", "--user-agent=" + userAgent);
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\chromedriver-win64\\chromedriver.exe");
            List<Position> list = new ArrayList<>();
            WebDriver driver = new ChromeDriver(options);
            String url = "https://www.zhaopin.com/sou/jl600/"+positionCode+"/p" + count + "?srccode="+cityCode;
            System.out.println("---------"+url);
            driver.get(url);
            Thread.sleep(5000);
            String html = driver.getPageSource();
            Document document = Jsoup.parse(html);

            Elements elements = document.getElementsByClass("joblist-box__iteminfo");
            System.out.println("--------------------第" + count + "页----------------------");
            elements.stream().filter(Objects::nonNull).forEach(element -> {
                String positionName = element.getElementsByClass("jobinfo__top").select("a[href]").text();
                System.out.println("第" + count + "页----------" + positionName);
                list.add(new Position(name,city,cityCode,positionName,positionCode,count));
            });
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
