package ck.ji.selenium.install;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SRT_auto_booking {
	
	
	public static void main(String[] args) throws InterruptedException {
		ChromeDriver driver;
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		WebElement tableRow;
		String valueIneedTableRow;
		
		//driver.findElement(By.cssSelector("#NM_FAVORITE > div.group_nav > ul.list_nav.NM_FAVORITE_LIST > li:nth-child(3) > a"));
		//driver.findElement(By.className("snb_search_text"));
		
		// SRT 사이트
				driver.get("https://etk.srail.kr/main.do");

				// main 창
				String strMain = driver.getWindowHandle();
				// main 창이 아니면 팝업창 닫기
				for (String handel : driver.getWindowHandles()) {
					if (!handel.equals(strMain)) {
						driver.switchTo().window(handel).close();
					}
				}

				// Main창으로 핸들러 바꿔주기
				driver.switchTo().window(strMain);

				// 출발역
				Select departure = new Select(driver.findElement(By.className("dropDownNoJqueryUi")));

				// 텍스트 값 선택
				departure.selectByVisibleText("광주송정");

				// 도착역
				Select arrival = new Select(driver.findElement(By.name("arvRsStnCd")));

				// 실제 수서역을 나타내는 값
				arrival.selectByValue("0551");

				// 시간 기다리기
//				driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);

				// 간편조회하기 버튼 클릭
				// id className 없을 때 a 태그로 들어가야 됨
				driver.findElement(By.cssSelector("#search-form > fieldset > a")).click();
				// 날짜 선택
				Thread.sleep(1000);
				Select date = new Select(driver.findElement(By.name("dptDt")));
				date.selectByValue("20230205");

				// 조회 버튼 다시 클릭
				driver.findElement(By.cssSelector("#search_top_tag > input")).click();

				// 예매하기 클릭
				Thread.sleep(1000);
				WebElement plz = driver.findElement(By.xpath("//*[@id=\"result-form\"]/fieldset/div[6]/table/tbody/tr[3]/td[7]/a"));
				Thread.sleep(1000);
				
				while (true) {
					//표가 없으면 무한 대기
					Thread.sleep(1000);
					WebElement baseTable = driver.findElement(By.tagName("table"));
					tableRow = baseTable.findElement(By.xpath("//*[@id=\"result-form\"]/fieldset/div[6]/table/tbody/tr[5]/td[7]/a"));
					valueIneedTableRow = tableRow.getText();
				
				if (valueIneedTableRow.equals("매진")) {
					driver.navigate().refresh();
					Thread.sleep(1000);

					System.out.println(valueIneedTableRow);
					
					System.out.println("새로고침 잘 되고 있음");
					continue;
					// 매진일 때 다시
				} else {
					plz.click();
					Thread.sleep(1000);
					// 아이디 입력
					WebElement id = driver.findElement(By.name("srchDvNm"));
					id.sendKeys("2396635136");
					// 비밀번호 입력
					WebElement pw = driver.findElement(By.name("hmpgPwdCphd"));
					pw.sendKeys("!a630623");
					// 엔터키 입력 보내기
					id.sendKeys(Keys.ENTER);
				 }
				// 결제하기 창 시작
				driver.findElement(By.cssSelector("#list-form > fieldset > div.tal_c > a.btn_large.btn_blue_dark.val_m.mgr10")).click();

				// 신용카드 정보
				WebElement card_number1 = driver.findElement(By.cssSelector("#stlCrCrdNo11"));
				card_number1.sendKeys("1234");

				WebElement card_number2 = driver.findElement(By.cssSelector("#stlCrCrdNo12"));
				card_number2.sendKeys("1234");

				WebElement card_number3 = driver.findElement(By.cssSelector("#stlCrCrdNo13"));
				card_number3.sendKeys("1234");

				driver.findElement(By.name("stlCrCrdNo14")).click();

				Thread.sleep(1000);
				driver.findElement(By.className("transkey_div_3_2")).click();
				driver.findElement(By.className("transkey_div_3_2")).click();
				driver.findElement(By.className("transkey_div_3_2")).click();
				driver.findElement(By.className("transkey_div_3_2")).click();

				// 유효기간
				Select month = new Select(driver.findElement(By.cssSelector("#crdVlidTrm1M")));
				month.selectByVisibleText("03");

				Select year = new Select(driver.findElement(By.cssSelector("#crdVlidTrm1Y")));
				year.selectByVisibleText("2026");

				// 비밀번호 입력
				WebElement pwd = driver.findElement(By.cssSelector("#vanPwd1"));
				driver.findElement(By.name("Tk_vanPwd1_checkbox")).click();		
				pwd.sendKeys("12");

				// 인증번호
				WebElement oknumber = driver.findElement(By.cssSelector("#athnVal1"));
				oknumber.sendKeys("980426");

				// 발권버튼
				driver.findElement(By.cssSelector("#requestIssue1")).click();

				// Alert 창 확인
				Alert alert = driver.switchTo().alert();
				alert.accept();
				
				break;
				}
				Thread.sleep(2000);
				System.out.println("예매가 완료됐습니다. \n프로그램을 종료합니다.");
				driver.quit();		
		

	}

}
