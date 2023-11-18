// COUNTDOWN
.global _start
startPoint:
    .int 5

subtractValueInt:
    .int 1

subtractValueFloat:
    .float 0.1

.section .text
_start:
    LDR R0, =startPoint
    LDR R1, [R0]    

loopInt:
    LDR R2, =subtractValueInt
    LDR R3, [R2]    
    SUB R1, R1, R3
    CMP R1, #1
    BEQ loopFloat

    BAL loopInt

loopFloat:
    LDR R4, =subtractValueFloat
    LDR R5, [R4]    
    SUB R1, R1, R5
    CMP R1, #0
    BEQ exit

    BAL loopFloat

exit:

.end

// the same In C
#include <stdio.h>
int main() {
	
    int start = 5;
	
    int e = 1;

    for (int i = start; i > 0; --i) {
        printf("%d\n", i);

        if (i == 1) {
            for (int j = 10; j > 0; --j) {
                printf("%0.1f\n", 1.0 - (0.1 * e));
                e++;
            }
        }
    }

    return 0;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// MULTIPLICATION
.global _start
.section .text
_start:
    MOV R0, #0 // RESULT
    MOV R1, #5
    MOV R2, #3
    MOV R3, #5 // Equals to R1's value(LDR r,=... -> LDR,[r])

loop:
    ADD R0,R0,R2
    SUB R3,R3,#1
    CMP R3, #0     
    BEQ exit

    BAL loop

exit:

.end 

// OR //     // OR //      // OR //     // OR //     // OR //     // OR //     // OR //     // OR //     // OR //     // OR //    // OR //
.global _start
.section .data
num1:
    .word 5
	
num2:
    .word 3

result:
    .word 0

.section .text
_start:

    LDR R1, =num1   

    LDR R2, =num2  

    LDR R3, [R1]     
    LDR R4, [R2]     
    MUL R0, R3, R4   
    STR R0, [result] 

.end
