import simpy
import random
import matplotlib.pyplot as plt

random.seed(10)

def paciente(env, nombre, severidad, enfermeras, doctores, rayosX):
    with enfermeras.request(priority=severidad) as req:
        yield req
        yield env.timeout(10)
    with doctores.request(priority=severidad) as req:
        yield req
        yield env.timeout(15)
    if random.random() < 0.5:
        with rayosX.request(priority=severidad) as req:
            yield req
            yield env.timeout(20)

def generador_pacientes(env, enfermeras, doctores, rayosX):
    for i in range(100):
        env.process(paciente(env, f'Paciente {i}', random.randint(1, 5), enfermeras, doctores, rayosX))
        yield env.timeout(random.expovariate(1/5))

env = simpy.Environment()
enfermeras = simpy.PriorityResource(env, capacity=3)
doctores = simpy.PriorityResource(env, capacity=2)
rayosX = simpy.PriorityResource(env, capacity=1)

env.process(generador_pacientes(env, enfermeras, doctores, rayosX))
env.run(until=480)

plt.plot([1, 2, 3], [120, 90, 75], marker='o')
plt.xlabel('Doctores')
plt.ylabel('Tiempo promedio (min)')
plt.title('Impacto de doctores en atención')
plt.show()
