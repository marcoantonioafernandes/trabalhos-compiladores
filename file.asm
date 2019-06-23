global _main
extern _printf
extern _putchar
extern _scanf
section .text

_funcao:
push ebp
push dword [_@DSP + (4)]
mov ebp, esp
mov [_@DSP + 4], ebp
sub esp, 4
push dword [EBP + (16)]push dword [EBP + (12)]pop dword[ebp + (20)]add esp, 4
mov esp, ebp
pop dword [_@DSP + 4]
pop ebp
ret
_main:
push ebp
push dword [_@DSP + (0)]
mov ebp, esp
mov [_@DSP + 0], ebp
sub esp, 8
push 4
push 2
call _funcaoadd esp, 8 pop dword[ebp + (-4)]mov dword[_@DSP +0 ], ebp
push dword[ebp +-4 ]
push _@STR1
call _printf
add esp, 8
add esp, 8
mov esp, ebp
pop dword [_@DSP + 0]
pop ebp
ret
section .data
_@STR1: db '%d', 0_@DSP: times 8 db 0