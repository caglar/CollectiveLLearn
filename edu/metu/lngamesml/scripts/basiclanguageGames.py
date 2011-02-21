import random


class Agent:
    _Role = 0 # 1 if speaker
    _CurrentWord = ""

    def setRole(role):
        self._Role = role

    def getRole():
        return self._Role

    def setCurrentWord(self, word):
        self._CurrentWord = word

    def getCurrentWord(self):
        return self._CurrentWord

    def hear(self, word):
        self.setCurrentWord(word)

    def speak(self):
        return self._CurrentWord

#        self._CurrentWord = self._Lexicon[n]

class Sim:
    _Lexicon = []
    _Agents = []
    _Map = {}
    _NoOfAgents = 10
    _NoOfSuccesses = 0
    _NoOfFailures = 0
    _NoOfIterations = 0

    def setLexicon(self, lex):
        self._Lexicon = lex

    def chooseRandomWord(self):
        l = len(self._Lexicon)
        n = random.randint(0, l-1)
        return self._Lexicon[n]

    def setNoOfAgents(self, noOfAgents):
        self.NoOfAgents = noOfAgents

    def getNoOfAgents(self):
        return self.NoOfAgents

    def getRandomAgent(self):
        l = len(self._Agents)
        rnd_val = random.randint(0, l-1)
        return self._Agents[rnd_val]

    def initAgents(self, noOfAgents=0):
        if (noOfAgents):
           self._NoOfAgents = noOfObjects
        for i in range(self._NoOfAgents):
            ag = Agent()
            rnd_word = self.chooseRandomWord()
            print rnd_word
            ag.setCurrentWord(rnd_word)
            self._Agents.append(ag)

    def _initMap(self):
        for i in range(self._NoOfAgents):
            word = self._Agents[i].getCurrentWord()
            if self._Map.has_key(word):
                self._Map[word] += 1
            else:
                self._Map[word] = 1

    def getNoOfSuccesses(self):
        return self._NoOfSuccesses

    def getNoOfFailures(self):
        return self._NoOfFailures

    def getNoOfIterations(self):
        return self._NoOfIterations

    def playGames(self):
           l = len(self._Agents)
           self._initMap()

           while(len(self._Map) > 1):
            role = random.choice([0, 1])
            agent1 = self.getRandomAgent()
            agent2 = self.getRandomAgent()
            self._NoOfIterations += 1
            noOfCategories = len(self._Map)
            print "No Of Categories " + str(noOfCategories)
            while(agent2 == agent1):
                agent2 = self.getRandomAgent()

            if agent1.speak() == agent2.speak():
                self._NoOfSuccesses += 1
            else:
                self._NoOfFailures += 1
                if role == 0:
                    lWord = agent2.speak()
                    sWord = agent1.speak()
                    self._Map[lWord] -= 1
                    self._Map[sWord] += 1
                    agent2.hear(sWord)
                    if self._Map[lWord] < 1:
                        del self._Map[lWord]
                else:
                    lWord = agent1.speak()
                    sWord = agent2.speak()
                    self._Map[lWord] -= 1
                    self._Map[sWord] += 1
                    agent1.hear(sWord)
                    if self._Map[lWord] == 0:
                        del self._Map[lWord]
                if len(self._Map) == 1:
                    break

def main():
    blg = Sim()
    lex = ["Araba", "Bebek", "Kitap", "Baba", "Anne", "Kelebek", "Su", "Mama",
            "Yemek", "Adda", "Dede", "Puf", "Da"]
    blg.setLexicon(lex)
    blg.setNoOfAgents(10)
    blg.initAgents()
    blg.playGames()
    noOfIterations = blg.getNoOfIterations()
    noOfSuccess = blg.getNoOfSuccesses()
    noOfFailures = blg.getNoOfFailures()
    print "Iterations " + str(noOfIterations)
    print "No of Successes " + str(noOfSuccess)
    print "No of Failures " + str(noOfFailures)

main()
