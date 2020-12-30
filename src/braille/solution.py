SPACE = 0 # 000000
CAPITAL_MARK = 32 # 000001

# https://en.wikipedia.org/wiki/Braille#Derivation
# A-J
CHAR_INT_MAPPING = {
    'a': 1,
    'b': 1 + 2,
    'c': 1 + 8,
    'd': 1 + 8 + 16,
    'e': 1 + 16,
    'f': 1 + 2 + 8,
    'g': 1 + 2 + 8 + 16,
    'h': 1 + 2 + 16,
    'i': 2 + 8,
    'j': 2 + 8 + 16
}

# K-T
for i in range(10):
    reference = CHAR_INT_MAPPING[chr(ord('a') + i)]
    c = chr(ord('a') + i + 10);
    CHAR_INT_MAPPING[c] = reference + 4

# W
CHAR_INT_MAPPING['w'] = 2 + 8 + 16 + 32

# U-V, X-Z
for i in range(5):
    reference = CHAR_INT_MAPPING[chr(ord('a') + i)]
    # We skipped W
    c = chr(ord('a') + i + 20) if i < 2 else chr(ord('a') + i + 21)
    CHAR_INT_MAPPING[c] = reference + 4 + 32

def solution(s):
    intRepresentation = []
    for c in s:
        if ' ' == c:
            intRepresentation.append(SPACE)
            continue

        if c.isupper():
            intRepresentation.append(CAPITAL_MARK)

        lower = c.lower()
        intRepresentation.append(CHAR_INT_MAPPING[lower])

    return ''.join([int2Braille(x) for x in intRepresentation])

def int2Braille(value):
    return '{:0>6b}'.format(value)[::-1]