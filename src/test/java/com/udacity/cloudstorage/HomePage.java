package com.udacity.cloudstorage;


import com.udacity.cloudstorage.entity.Credential;
import com.udacity.cloudstorage.entity.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    @FindBy(id = "btnLogout")
    private WebElement logoutButton;

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "btnAddNewNote")
    private WebElement btnAddNewNote;

    @FindBy(id = "btnAddNewCredential")
    private WebElement btnAddNewCredential;

    @FindBy(id = "note-title")
    private WebElement txtNoteTitle;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "note-description")
    private WebElement txtNoteDescription;

    @FindBy(id = "btnSaveChanges")
    private WebElement btnSaveChanges;

    @FindBy(id = "tableNoteTitle")
    private WebElement tableNoteTitle;

    @FindBy(id = "tableNoteDescription")
    private WebElement tableNoteDescription;

    @FindBy(id = "btnEditNote")
    private WebElement btnEditNote;

    @FindBy(id = "btnEditCredential")
    private WebElement btnEditCredential;

    @FindBy(id = "note-description")
    private WebElement txtModifyNoteDescription;

    @FindBy(id = "ancDeleteNote")
    private WebElement ancDeleteNote;

    @FindBy(id = "aDeleteCredential")
    private WebElement aDeleteCredential;

    @FindBy(id = "credential-url")
    private WebElement txtCredentialUrl;

    @FindBy(id = "credential-username")
    private WebElement txtCredentialUsername;

    @FindBy(id = "credential-password")
    private WebElement txtCredentialPassword;

    @FindBy(id = "btnCredentialSaveChanges")
    private WebElement btnCredentialSaveChanges;

    @FindBy(id = "tblCredentialUrl")
    private WebElement tblCredentialUrl;

    @FindBy(id = "tblCredentialUsername")
    private WebElement tblCredentialUsername;

    @FindBy(id = "tblCredentialPassword")
    private WebElement tblCredentialPassword;

    private final JavascriptExecutor js;

    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        js = (JavascriptExecutor) driver;
        wait = new WebDriverWait(driver, 500);
    }

    public void logout() {
        js.executeScript("arguments[0].click();", logoutButton);
    }

    public void editNote() {
        js.executeScript("arguments[0].click();", btnEditNote);
    }

    public void editCredential() {
        js.executeScript("arguments[0].click();", btnEditCredential);
    }

    public void deleteNote() {
        js.executeScript("arguments[0].click();", ancDeleteNote);
    }

    public void deleteCredential() {
        js.executeScript("arguments[0].click();", aDeleteCredential);
    }

    public void uploadFile() {
        js.executeScript("arguments[0].click();", fileUpload);
    }

    public void addNewNote() {
        js.executeScript("arguments[0].click();", btnAddNewNote);
    }

    public void addNewCredential() {
        js.executeScript("arguments[0].click();", btnAddNewCredential);
    }

    public void setNoteTitle(String noteTitle) {
        js.executeScript("arguments[0].value='" + noteTitle + "';", txtNoteTitle);
    }

    public void setCredentialUrl(String url) {
        js.executeScript("arguments[0].value='" + url + "';", txtCredentialUrl);
    }

    public void setCredentialUsername(String username) {
        js.executeScript("arguments[0].value='" + username + "';", txtCredentialUsername);
    }

    public void setCredentialPassword(String password) {
        js.executeScript("arguments[0].value='" + password + "';", txtCredentialPassword);
    }

    public void modifyNoteTitle(String newNoteTitle) {
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtNoteTitle)).sendKeys(newNoteTitle);
    }

    public void modifyNoteDescription(String newNoteDescription) {
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).clear();
        wait.until(ExpectedConditions.elementToBeClickable(txtModifyNoteDescription)).sendKeys(newNoteDescription);
    }

    public void navToNotesTab() {
        js.executeScript("arguments[0].click();", navNotesTab);
    }

    public void navToCredentialsTab() {
        js.executeScript("arguments[0].click();", navCredentialsTab);
    }

    public void setNoteDescription(String noteDescription) {
        js.executeScript("arguments[0].value='"+ noteDescription +"';", txtNoteDescription);
    }

    public void saveNoteChanges() {
        js.executeScript("arguments[0].click();", btnSaveChanges);
    }

    public void saveCredentialChanges() {
        js.executeScript("arguments[0].click();", btnCredentialSaveChanges);
    }

    public boolean noNotes(WebDriver driver) {
        return !isElementPresent(By.id("tableNoteTitle"), driver) && !isElementPresent(By.id("tableNoteDescription"), driver);
    }

    public boolean noCredentials(WebDriver driver) {
        return !isElementPresent(By.id("tblCredentialUrl"), driver) &&
                !isElementPresent(By.id("tblCredentialUsername"), driver) &&
                !isElementPresent(By.id("tblCredentialPassword"), driver);
    }

    public boolean isElementPresent(By locatorKey, WebDriver driver) {
        try {
            driver.findElement(locatorKey);

            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public Note getFirstNote() {
        String title = wait.until(ExpectedConditions.elementToBeClickable(tableNoteTitle)).getText();
        String description = tableNoteDescription.getText();

        return new Note(title, description);
    }

    public Credential getFirstCredential() {
        String url = wait.until(ExpectedConditions.elementToBeClickable(tblCredentialUrl)).getText();
        String username = tblCredentialUsername.getText();
        String password = tblCredentialPassword.getText();

        return new Credential(url, username, password);
    }

    public WebElement getLogoutButton() {
        return logoutButton;
    }

    public void setLogoutButton(WebElement logoutButton) {
        this.logoutButton = logoutButton;
    }

    public WebElement getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(WebElement fileUpload) {
        this.fileUpload = fileUpload;
    }

    public WebElement getBtnAddNewNote() {
        return btnAddNewNote;
    }

    public void setBtnAddNewNote(WebElement btnAddNewNote) {
        this.btnAddNewNote = btnAddNewNote;
    }

    public WebElement getBtnAddNewCredential() {
        return btnAddNewCredential;
    }

    public void setBtnAddNewCredential(WebElement btnAddNewCredential) {
        this.btnAddNewCredential = btnAddNewCredential;
    }

    public WebElement getTxtNoteTitle() {
        return txtNoteTitle;
    }

    public void setTxtNoteTitle(WebElement txtNoteTitle) {
        this.txtNoteTitle = txtNoteTitle;
    }

    public WebElement getNavNotesTab() {
        return navNotesTab;
    }

    public void setNavNotesTab(WebElement navNotesTab) {
        this.navNotesTab = navNotesTab;
    }

    public WebElement getNavCredentialsTab() {
        return navCredentialsTab;
    }

    public void setNavCredentialsTab(WebElement navCredentialsTab) {
        this.navCredentialsTab = navCredentialsTab;
    }

    public WebElement getTxtNoteDescription() {
        return txtNoteDescription;
    }

    public void setTxtNoteDescription(WebElement txtNoteDescription) {
        this.txtNoteDescription = txtNoteDescription;
    }

    public WebElement getBtnSaveChanges() {
        return btnSaveChanges;
    }

    public void setBtnSaveChanges(WebElement btnSaveChanges) {
        this.btnSaveChanges = btnSaveChanges;
    }

    public WebElement getTableNoteTitle() {
        return tableNoteTitle;
    }

    public void setTableNoteTitle(WebElement tableNoteTitle) {
        this.tableNoteTitle = tableNoteTitle;
    }

    public WebElement getTableNoteDescription() {
        return tableNoteDescription;
    }

    public void setTableNoteDescription(WebElement tableNoteDescription) {
        this.tableNoteDescription = tableNoteDescription;
    }

    public WebElement getBtnEditNote() {
        return btnEditNote;
    }

    public void setBtnEditNote(WebElement btnEditNote) {
        this.btnEditNote = btnEditNote;
    }

    public WebElement getBtnEditCredential() {
        return btnEditCredential;
    }

    public void setBtnEditCredential(WebElement btnEditCredential) {
        this.btnEditCredential = btnEditCredential;
    }

    public WebElement getTxtModifyNoteDescription() {
        return txtModifyNoteDescription;
    }

    public void setTxtModifyNoteDescription(WebElement txtModifyNoteDescription) {
        this.txtModifyNoteDescription = txtModifyNoteDescription;
    }

    public WebElement getAncDeleteNote() {
        return ancDeleteNote;
    }

    public void setAncDeleteNote(WebElement ancDeleteNote) {
        this.ancDeleteNote = ancDeleteNote;
    }

    public WebElement getaDeleteCredential() {
        return aDeleteCredential;
    }

    public void setaDeleteCredential(WebElement aDeleteCredential) {
        this.aDeleteCredential = aDeleteCredential;
    }

    public WebElement getTxtCredentialUrl() {
        return txtCredentialUrl;
    }

    public void setTxtCredentialUrl(WebElement txtCredentialUrl) {
        this.txtCredentialUrl = txtCredentialUrl;
    }

    public WebElement getTxtCredentialUsername() {
        return txtCredentialUsername;
    }

    public void setTxtCredentialUsername(WebElement txtCredentialUsername) {
        this.txtCredentialUsername = txtCredentialUsername;
    }

    public WebElement getTxtCredentialPassword() {
        return txtCredentialPassword;
    }

    public void setTxtCredentialPassword(WebElement txtCredentialPassword) {
        this.txtCredentialPassword = txtCredentialPassword;
    }

    public WebElement getBtnCredentialSaveChanges() {
        return btnCredentialSaveChanges;
    }

    public void setBtnCredentialSaveChanges(WebElement btnCredentialSaveChanges) {
        this.btnCredentialSaveChanges = btnCredentialSaveChanges;
    }

    public WebElement getTblCredentialUrl() {
        return tblCredentialUrl;
    }

    public void setTblCredentialUrl(WebElement tblCredentialUrl) {
        this.tblCredentialUrl = tblCredentialUrl;
    }

    public WebElement getTblCredentialUsername() {
        return tblCredentialUsername;
    }

    public void setTblCredentialUsername(WebElement tblCredentialUsername) {
        this.tblCredentialUsername = tblCredentialUsername;
    }

    public WebElement getTblCredentialPassword() {
        return tblCredentialPassword;
    }

    public void setTblCredentialPassword(WebElement tblCredentialPassword) {
        this.tblCredentialPassword = tblCredentialPassword;
    }

    public JavascriptExecutor getJs() {
        return js;
    }

    public WebDriverWait getWait() {
        return wait;
    }
}
