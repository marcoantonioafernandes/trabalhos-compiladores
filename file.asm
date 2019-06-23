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
push 0 
pop dword[ebp + (-4)] 
rotuloWhile0: 
push dword [EBP + (-4)] 
push 3 
pop eax
cmp dword [ESP], eax 
je Falso0 
mov dword [ESP], 1
jmp Fim0 
Falso0: 
mov dword [ESP], 0 
Fim0: 
pop eax 
cmp eax, 1 
jne rotuloFim1 
push dword [EBP + (-4)] 
push 1 
pop eax
add dword[ESP], eax
pop dword[ebp + (-4)] 
mov dword[_@DSP +0 ], ebp
push dword[ebp + (-4) ]
push _@STR1
call _printf
add esp, 8
jmp rotuloWhile0 
rotuloFim1:
add esp, 12
mov esp, ebp
pop dword [_@DSP + 0]
pop ebp
ret
section .data
_@STR1: db '%d', 0
_@DSP: times 4 db 0