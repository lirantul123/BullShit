<?php
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $name = $_POST['name'];
    $email = $_POST['email'];
    $message = $_POST['message'];

    $to = 'lira.tulchin@gmail.com';
    $subject = 'New Contact Form Submission';
    $content = "Name: $name\nEmail: $email\nMessage: $message";

    $headers = "From: $email\r\n";
    $headers .= "Reply-To: $email\r\n";

    if (mail($to, $subject, $content, $headers)) {
        echo 'Email sent successfully!';
    } else {
        echo 'Email could not be sent.';
    }
}
?>
