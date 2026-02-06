import unittest
import time
from selenium import webdriver
from selenium.webdriver.firefox.service import Service
from webdriver_manager.firefox import GeckoDriverManager
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

class ValiantSystemsSuite(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        """Viene eseguito una sola volta all'inizio di tutta la suite"""
        service = Service(GeckoDriverManager().install())
        cls.driver = webdriver.Firefox(service=service)
        cls.wait = WebDriverWait(cls.driver, 10)
        cls.driver.maximize_window()

    def evidenzia(self, element):
        """Evidenzia l'elemento per rendere il test visibile"""
        self.driver.execute_script("arguments[0].setAttribute('style', 'border: 3px solid red; background: yellow;');", element)
        time.sleep(1)

    # --- TEST 1: LOGIN ---
    def test_01_login(self):
        print("\n--- Running: Test Login ---")
        self.driver.get("http://localhost:8080/Valiant_Systems-1.0-SNAPSHOT/")
        
        link_login = self.wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, '[title="Accedi/Registrati"]')))
        self.evidenzia(link_login)
        link_login.click()
        
        self.wait.until(EC.presence_of_element_located((By.ID, "username"))).send_keys("Antcerr04")
        self.driver.find_element(By.ID, "password").send_keys("Antonio2004@")
        
        btn_submit = self.driver.find_element(By.CSS_SELECTOR, 'input[type="submit"]')
        self.evidenzia(btn_submit)
        btn_submit.click()
        
        # Verifica di essere tornati alla index (o Dashboard)
        time.sleep(1)
        self.assertIn("index", self.driver.current_url.lower(), "Login fallito: non siamo nella index")

    # --- TEST 2: VISUALIZZA INVENTARIO ---
    def test_02_visualizza_inventario(self):
        print("--- Running: Test Visualizza Inventario ---")
        link_lista = self.wait.until(EC.element_to_be_clickable((By.LINK_TEXT, "Lista PC")))
        self.evidenzia(link_lista)
        link_lista.click()
        
        # Verifica che la pagina contenga dei prodotti (almeno un tasto 'Visualizza')
        pulsanti_visualizza = self.wait.until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, 'input[value="Visualizza"]')))
        self.assertGreater(len(pulsanti_visualizza), 0, "Inventario vuoto o non caricato")

    # --- TEST 3: VISUALIZZA PRODOTTO ---
    def test_03_visualizza_prodotto(self):
        print("--- Running: Test Visualizza Dettaglio Prodotto ---")
        primo_visualizza = self.wait.until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, 'input[value="Visualizza"]')))[0]
        self.evidenzia(primo_visualizza)
        primo_visualizza.click()
        
        # Verifica che siamo nella pagina dettaglio (cerchiamo il tasto aggiungi al carrello)
        btn_check = self.wait.until(EC.presence_of_element_located((By.CSS_SELECTOR, 'input[value="Aggiungi al carrello"]')))
        self.assertTrue(btn_check.is_displayed(), "Pagina prodotto non caricata correttamente")

    # --- TEST 4: AGGIUNGI PRODOTTO NEL CARRELLO ---
    def test_04_aggiungi_al_carrello(self):
        print("--- Running: Test Aggiunta al Carrello ---")
        btn_carrello = self.wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, 'input[type="submit"][value="Aggiungi al carrello"]')))
        self.evidenzia(btn_carrello)
        btn_carrello.click()
        
        # Qui potresti aggiungere un controllo: es. verificare se compare un messaggio 'Prodotto aggiunto'
        time.sleep(1)

    # --- TEST 5: EFFETTUA ORDINE ---
    def test_05_effettua_ordine(self):
        print("--- Running: Test Effettua Ordine ---")
        btn_ordina = self.wait.until(EC.element_to_be_clickable((By.XPATH, "//*[contains(text(), 'Ordina')]")))
        self.evidenzia(btn_ordina)
        btn_ordina.click()
        
        print("✅ Flusso completo terminato con successo!")

    @classmethod
    def tearDownClass(cls):
        """Viene eseguito alla fine di tutti i test"""
        time.sleep(5)
        cls.driver.quit()

if __name__ == "__main__":
    unittest.main()