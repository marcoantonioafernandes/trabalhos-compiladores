global _main
extern _printf
extern _putchar
extern _scanf
section .text

_main:
push ebp
push dword [_@DSP + (0)]
mov ebp, esp
mov [_@DSP + 0], ebp
sub esp, 12
mov edx, ebp 
lea eax, [edx + -4] 
push eax 
push _@Integer 
call _scanf 
add esp, 8 
mov dword[_@DSP +0 ], ebp
push dword[ebp + (-4) ]
push _@STR1
call _printf
add esp, 8
push _@STR2
call _printf
add esp, 4
add esp, 12
mov esp, ebp
pop dword [_@DSP + 0]
pop ebp
ret
section .data
_@Integer: db '%d',0 
_@STR1: db '%d',10, 0 
_@STR2: db 'teste',10 , 0 
_@DSP: times 4 db 0