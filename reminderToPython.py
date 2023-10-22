# Online Python compiler (interpreter) to run Python online.
# Write Python 3 code in this online editor and run it.

class Animal:
    def __init__(self, species, name, age):
        self.species = species
        self.name = name
        self.age = age
    def getName(self):
        return self.name
    def getAge(self):
        return self.age
    def setName(self, newName):
        self.name = newName
    def setAge(self, newAge):
        self.age = newAge


def length_of_longest_subarray(nums):
    newNums = []
    b = True
    countArr = []
    
    for i in range(len(nums)):
        currCount = methodWithValue(nums, nums[i])
        countArr.append(currCount)  

    minWillBeRemoved = min(countArr)  

    for i in range(len(countArr)):
        if countArr[i] != minWillBeRemoved:
            newNums.append(nums[i])  
    return newNums

def methodWithValue(nums, value):
    currCount = 0
    for i in range(len(nums)):
        if nums[i] == value:
            currCount += 1
    return currCount

newArr = length_of_longest_subarray([3, 2, 4, 2, 3])
print(newArr, "-  length: ", len(newArr)) 
my_pet = Animal("Dog", "Buddy", 3)
