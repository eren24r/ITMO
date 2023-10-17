import subprocess

test_cases = [
    {
        'input': 'first_w',
        'expected_output': 'first_word_explanation',
        'expected_error': ''
    },
    {
        'input': 'second_w',
        'expected_output': 'second_word_explanation',
        'expected_error': ''
    },
    {
        'input': 'lab2',
        'expected_output': 'lab2',
        'expected_error': ''
    },
    {
        'input': 'lab3',
        'expected_output': '',
        'expected_error': 'Key not found.'
    },
    {
        'input': 'lab2' * 50,
        'expected_output': '\n',
        'expected_error': 'Key is too long (>255).'
    }
]

for test_case in test_cases:
    process = subprocess.run("./program", input=test_case['input'], text=True, capture_output=True)

    if process.stdout == test_case['expected_output'] and process.stderr == test_case['expected_error']:
        print(f"[OK] {test_case['input']}")
    else:
        print(f"[ERROR]")
        print("Input:", test_case['input'])
        print("Expected Output:", test_case['expected_output'])
        print("Expected Error:", test_case['expected_error'])
        print("Actual Output:", repr(process.stdout))
        print("Actual Error:", repr(process.stderr), end='\n\n')

