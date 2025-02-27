function getPatientIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('id');
}

function createChart(canvasId, apiUrl) {
    fetch(apiUrl)
        .then(response => response.json())
        .then(data => {
            if (!Array.isArray(data)) {
                throw new Error("Données invalides : l'API ne retourne pas un tableau !");
            }

            let labels = data.map(item => item.measureDate);
            let temperatures = data.map(item => item.temperature);
            let pulses = data.map(item => item.pulse);
            let spo2s = data.map(item => item.SpO2);
            let albumins = data.map(item => item.albumin);
            let bloodGlucoses = data.map(item => item.bloodGlucose);
            let bmis = data.map(item => item.bmi);
            let infos = data.map(item => item.info);

            var ctx = document.getElementById(canvasId).getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [
                        { label: 'Température (°C)', data: temperatures, borderColor: 'red', borderWidth: 2, fill: false },
                        { label: 'Pouls (bpm)', data: pulses, borderColor: 'blue', borderWidth: 2, fill: false },
                        { label: 'Saturation O2 (%)', data: spo2s, borderColor: 'green', borderWidth: 2, fill: false },
                        { label: 'Albumine (g.L-1)', data: albumins, borderColor: 'yellow', borderWidth: 2, fill: false },
                        { label: 'Glycémie (g.L-1)', data: bloodGlucoses, borderColor: 'pink', borderWidth: 2, fill: false },
                        { label: 'IMC', data: bmis, borderColor: 'purple', borderWidth: 2, fill: false }
                    ]
                },
                options: {
                    responsive: true,
                    plugins: {
                        tooltip: {
                            callbacks: {
                                label: function (tooltipItem) {
                                    return infos[tooltipItem.dataIndex];
                                }
                            }
                        }
                    }
                }
            });

            // Gestion des cases à cocher pour afficher/masquer les courbes
            document.getElementById("toggleTemp").addEventListener("change", function () {
                myChart.data.datasets[0].hidden = !this.checked;
                myChart.update();
            });

            document.getElementById("togglePulse").addEventListener("change", function () {
                myChart.data.datasets[1].hidden = !this.checked;
                myChart.update();
            });

            document.getElementById("toggleSpO2").addEventListener("change", function () {
                myChart.data.datasets[2].hidden = !this.checked;
                myChart.update();
            });

            document.getElementById("toggleAlbumin").addEventListener("change", function () {
                myChart.data.datasets[3].hidden = !this.checked;
                myChart.update();
            });

            document.getElementById("toggleGlycemia").addEventListener("change", function () {
                myChart.data.datasets[4].hidden = !this.checked;
                myChart.update();
            });

            document.getElementById("toggleBmi").addEventListener("change", function () {
                myChart.data.datasets[5].hidden = !this.checked;
                myChart.update();
            });

        })
        .catch(error => console.error("Erreur lors de la récupération des données :", error));
}

document.addEventListener("DOMContentLoaded", function () {
    const patientId = getPatientIdFromUrl();
    if (!patientId) {
        console.error("Aucun ID de patient trouvé dans l'URL !");
        return;
    }

    createChart("chartVitalSigns", `http://localhost:8080/admin/patient/vital-signs/${patientId}`);
});
