.global _start

.section .data
    hello db 'Hello, World!', 0

.section .text
    global _start

_start:
    ; write the message to stdout
    mov eax, 4            ; system call number (sys_write)
    mov ebx, 1            ; file descriptor (stdout)
    mov ecx, hello        ; pointer to the message
    mov edx, 13           ; message length
    int 0x80              ; call kernel

    ; exit the program
    mov eax, 1            ; system call number (sys_exit)
    xor ebx, ebx          ; exit code 0
    int 0x80              ; call kernel
