document.getElementById('bookForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission
    const bookName = document.getElementById('bookName').value;
    const bookEdition = document.getElementById('bookEdition').value;
    const bookPrice = document.getElementById('bookPrice').value;

    // Simulate adding the book to a list (you can replace this with actual functionality)
    alert(`Record for "${bookName}" has been registered successfully!\nEdition: ${bookEdition}\nPrice: ${bookPrice}`);

    // Reset the form
    this.reset();
});
/**
 * 
 */