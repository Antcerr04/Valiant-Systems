import unittest
import time
from selenium import webdriver
from selenium.webdriver.firefox.service import Service
from webdriver_manager.firefox import GeckoDriverManager
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

class TestManagerProdotti(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        service = Service(GeckoDriverManager().install())
        cls.driver = webdriver.Firefox(service=service)
        cls.wait = WebDriverWait(cls.driver, 10)
        cls.driver.maximize_window()

    def evidenzia(self, element):
        self.driver.execute_script("arguments[0].setAttribute('style', 'border: 3px solid red; background: yellow;');", element)
        time.sleep(1.2)

    def login_admin(self):
        """Metodo di supporto per loggare l'admin se necessario"""
        self.driver.get("http://localhost:8080/Valiant_Systems-1.0-SNAPSHOT/")
        try:
            link_login = self.wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, '[title="Accedi/Registrati"]')))
            link_login.click()
            self.wait.until(EC.presence_of_element_located((By.ID, "username"))).send_keys("admin")
            self.driver.find_element(By.ID, "password").send_keys("Admin01@")
            self.driver.find_element(By.CSS_SELECTOR, 'input[type="submit"]').click()
            time.sleep(1)
        except:
            print("Già loggato o errore login")

    # --- SUITE 1: INSERIMENTO PRODOTTO ---
    def test_01_inserimento_prodotto(self):
        print("\n--- Inizio Suite: Inserimento Prodotto ---")
        self.login_admin()

        # Navigazione
        menu = self.wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, '[title="Menu utente"]')))
        self.evidenzia(menu)
        menu.click()

        link_ins = self.wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, '[title="Inserisci un nuovo PC"]')))
        self.evidenzia(link_ins)
        link_ins.click()

        # Compilazione
        campi = {
            "nameProdotto": "prodotto Prova",
            "prezzoProdotto": "1500",
            "percentualeSaldo": "1",
            "quantità": "2",
            "cpu": "cpuprova",
            "gpu": "gpuprova",
            "RAMsize": "256",
            "RAMspeed": "1000",
            "SSDsize": "256"
        }

        for c_id, val in campi.items():
            el = self.wait.until(EC.presence_of_element_located((By.ID, c_id)))
            self.evidenzia(el)
            el.clear()
            el.send_keys(val)

        btn_crea = self.driver.find_element(By.CSS_SELECTOR, 'input[value="Crea prodotto"]')
        self.evidenzia(btn_crea)
        btn_crea.click()
        print("✅ Prodotto inserito.")
        time.sleep(2)

    # --- SUITE 2: RIMOZIONE PRODOTTO ---
    def test_02_rimozione_prodotto(self):
        print("\n--- Inizio Suite: Rimozione Prodotto ---")
        # Torna al menu
        menu = self.wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, '[title="Menu utente"]')))
        self.evidenzia(menu)
        menu.click()

        link_inv = self.wait.until(EC.element_to_be_clickable((By.CSS_SELECTOR, "[title=\"Vai all'inventario dei PC\"]")))
        self.evidenzia(link_inv)
        link_inv.click()

        # Trova l'ULTIMO bottone elimina
        print("Ricerca dell'ultimo prodotto inserito...")
        bottoni_elimina = self.wait.until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, '[title="Elimina prodotto"]')))
        ultimo_bottone = bottoni_elimina[-1] # Prende l'ultimo elemento della lista
        
        self.evidenzia(ultimo_bottone)
        ultimo_bottone.click()

        # Gestione Alert (Popup OK/Annulla)
        print("Gestione Alert di conferma...")
        try:
            self.wait.until(EC.alert_is_present())
            alert = self.driver.switch_to.alert
            print(f"Messaggio alert: {alert.text}")
            time.sleep(1)
            alert.accept() # Clicca su OK
            print("✅ Alert accettato, prodotto rimosso.")
        except:
            print("Nessun alert apparso.")

        time.sleep(3)

    @classmethod
    def tearDownClass(cls):
        print("\nTest finiti. Chiusura sessione.")
        cls.driver.quit()

if __name__ == "__main__":
    unittest.main()