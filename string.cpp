#include <iostream>
#include <stack>
#include <cctype>

bool isOperator(char ch);
int precedence(char op);
void applyOperator(std::stack<int>& values, std::stack<char>& operators, char op);

int eval(const std::string& expression) {
    std::stack<int> values;
    std::stack<char> operators;

    for (char ch : expression) {
        if (std::isdigit(ch)) {
            values.push(ch - '0');
        } else if (isOperator(ch)) {
            while (!operators.empty() && precedence(operators.top()) >= precedence(ch)) {
                applyOperator(values, operators, operators.top());
                operators.pop();
            }
            operators.push(ch);
        }
    }

    while (!operators.empty()) {
        applyOperator(values, operators, operators.top());
        operators.pop();
    }

    return values.top();
}

bool isOperator(char ch) {
    return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^';
}

int precedence(char op) {
    switch (op) {
        case '+':
        case '-':
            return 1;
        case '*':
        case '/':
            return 2;
        case '^':
            return 3;
        default:
            return 0;
    }
}

void applyOperator(std::stack<int>& values, std::stack<char>& operators, char op) {
    int operand2 = values.top();
    values.pop();
    int operand1 = values.top();
    values.pop();

    switch (op) {
        case '+':
            values.push(operand1 + operand2);
            break;
        case '-':
            values.push(operand1 - operand2);
            break;
        case '*':
            values.push(operand1 * operand2);
            break;
        case '/':
            values.push(operand1 / operand2);
            break;
        case '^':
            int ans=1;
            for (int i =0; i < operand2; i++) { ans *= operand1; }
            values.push(ans);
            break;

    }

    std::cout << operand1 << ' ' << op << ' ' << operand2 << " = " << values.top() << std::endl;
}

int main() {
    std::string expression;
    std::cout << "Enter an arithmetic expression: ";
    std::getline(std::cin, expression);
    int result = eval(expression);
    
    std::cout << "\n";
    std::cout << expression << " = " << result << std::endl;

    return 0;
}
