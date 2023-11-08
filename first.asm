section .bss
    age resb 2

section .text
    global _start

_start:
    ; Display a prompt to enter the age
    mov eax, 4         ; syscall number for sys_write
    mov ebx, 1         ; file descriptor 1 (stdout)
    mov ecx, prompt    ; address of the message
    mov edx, prompt_len ; message length
    int 0x80           ; call kernel

    ; Read age from user input
    mov eax, 3         ; syscall number for sys_read
    mov ebx, 0         ; file descriptor 0 (stdin)
    mov ecx, age       ; buffer to store the age
    mov edx, 2         ; number of bytes to read
    int 0x80           ; call kernel

    ; Convert the input to an integer
    mov eax, 0         ; clear EAX
    mov al, [age]      ; load the first byte (tens digit)
    sub al, '0'        ; convert ASCII to integer
    imul eax, eax, 10  ; multiply by 10 (shift left)
    mov al, [age+1]    ; load the second byte (ones digit)
    sub al, '0'        ; convert ASCII to integer

    ; Check if the age is less than 18
    cmp eax, 18
    jl too_young

    ; If the age is 18 or older, the person can enter
    mov eax, 4         ; syscall number for sys_write
    mov ebx, 1         ; file descriptor 1 (stdout)
    mov ecx, allowed   ; address of the "Allowed" message
    mov edx, allowed_len ; message length
    int 0x80           ; call kernel

    ; Exit the program
    mov eax, 1         ; syscall number for sys_exit
    xor ebx, ebx        ; exit status (0)
    int 0x80           ; call kernel

too_young:
    ; Display a message that the person is too young
    mov eax, 4         ; syscall number for sys_write
    mov ebx, 1         ; file descriptor 1 (stdout)
    mov ecx, too_young_msg ; address of the "Too young" message
    mov edx, too_young_len ; message length
    int 0x80           ; call kernel

    ; Exit the program
    mov eax, 1         ; syscall number for sys_exit
    mov ebx, 1         ; exit status (1)
    int 0x80           ; call kernel

section .data
    prompt db 'Enter your age: ',0
    prompt_len equ $ - prompt

    allowed db 'You are allowed to enter the party.',10,0
    allowed_len equ $ - allowed

    too_young_msg db 'Sorry, you are too young to enter the party.',10,0
    too_young_len equ $ - too_young_msg
