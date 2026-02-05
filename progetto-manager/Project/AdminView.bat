@echo off
cd /d %~dp0

:: Lanciamo javaw con 'start /b' per chiudere il CMD immediatamente
:: Manteniamo il classpath doppio per sicurezza
start /b javaw -cp "target\classes;target\progetto-web-jar-with-dependencies.jar" org.example.Main

:: Il comando exit chiude la finestra nera mentre l'app rimane aperta
exit