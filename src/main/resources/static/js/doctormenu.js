document.getElementById("service").addEventListener("change", function() {
    let service = this.value;
    let doctorDropdown = document.getElementById("doctor");

    doctorDropdown.innerHTML = '<option value="">-- Sélectionnez un médecin --</option>';

    if (service) {
        fetch(`/admin/get-doctors?service=${service}`)
            .then(response => response.json())
            .then(data => {
                data.forEach(doctor => {
                    let option = document.createElement("option");
                    option.value = doctor.adminid;
                    option.textContent = doctor.name;
                    doctorDropdown.appendChild(option);
                });
            })
            .catch(error => console.error("Erreur lors du chargement des médecins :", error));
    }
});